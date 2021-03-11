package com.zlutil.tools.toolpackage.JavaBasic;

import com.zlutil.tools.toolpackage.JavaBasic.MyIO.MyIOUtil;
import com.zlutil.tools.toolpackage.JavaBasic.NetTools.DownLoad_My_Configs;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.*;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 向文件写入字节流时，off控制待写入文件的位置，len控制待写入文件的可写入数据长度
 */
public class la {
    public long count = 0;
    HttpClient httpClient = HttpClients.createDefault();
    public long startTime = System.currentTimeMillis();

    public static void main(String[] args) throws IOException, InterruptedException {
        //FileUtil.writeBytes(new byte[]{0,1,2,3,4},new File("C:\\Users\\12733\\Desktop\\a.txt"));
        try{
            String pdfFile = "C:\\Users\\12733\\Desktop\\运维管理系统使用手册（1230）.pdf";
            PDDocument doc = PDDocument.load(new File(pdfFile));
            int pagenumber = doc.getNumberOfPages();
            pdfFile = pdfFile.substring(0, pdfFile.lastIndexOf("."));
            String fileName = pdfFile + ".doc";
            File file = new File(fileName);
            if (!file.exists()){
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(fileName);
            Writer writer = new OutputStreamWriter(fos, "UTF-8");
            PDFTextStripper stripper = new PDFTextStripper();
            stripper.setSortByPosition(true);// 排序
            stripper.setStartPage(1);// 设置转换的开始页
            stripper.setEndPage(pagenumber);// 设置转换的结束页
            stripper.writeText(doc, writer);
            writer.close();
            doc.close();
            System.out.println("pdf转换word成功！");
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }





//        String path = "C:\\可视化资源文件列表\\解压目录\\上海市城市总体规划（2017-2035年）\\310000上海市城市总体规划电子成果数据";
//        File files = new File(path);
//        List<String> file = Arrays.asList(files.list());
//
//
//
//        file.stream().forEachOrdered(System.out::println);
        //String remoteFile = "http://localhost/Java性能优化权威指南.pdf";
        //String remoteFile = "http://localhost/大江大河.mp4";
        //String targetFile = "E:\\【搜狐】孤独的美食家1-4季\\cc\\a.mp4";
        //String targetFile = "C:\\Users\\12733\\Desktop\\a.mp4";
        //String targetFile = "C:\\Users\\12733\\Desktop\\a.pdf";
        //String targetFile = "C:\\Users\\12733\\Desktop\\a.txt";

        //new la().muiltDownload(remoteFile, targetFile, 10);
        //new la().getContentLength(remoteFile);
        //long start1 = System.currentTimeMillis();
        //HttpUtil.downloadFile(remoteFile, new File(targetFile));
        //System.out.println("同步耗时: " + (System.currentTimeMillis() - start1) + "\n");





    public void muiltDownload(String url, String targetFilepath, Integer threadNum) throws IOException {
        long contentLength = this.getContentLength(url);
        long splitSize = contentLength / threadNum;

        if (threadNum > contentLength) {
            return;//线程数大于文件则中断执行
        }

        ExecutorService executorService = Executors.newCachedThreadPool();

        for (int i = 0; i < threadNum; i++) {
            long start = i == 0 ? i * splitSize : i * splitSize + 1;
            long end = i == (threadNum - 1) ? contentLength : ((i + 1) * splitSize);
            executorService.execute(() -> muiltDownloadRunner(url, targetFilepath, start, end, threadNum));
        }
    }

    public void muiltDownloadRunner(String url
            , String targetFilepath
            , long start
            , long end
            , long threadNum) {
        try {
            HttpGet httpGet = new HttpGet(url);
            httpGet.addHeader("User-Agent", DownLoad_My_Configs.httpGet_Header);
            httpGet.addHeader("Range", "bytes=" + start + "-" + end);
            HttpResponse response = httpClient.execute(httpGet);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            response.getEntity().writeTo(outputStream);//转换成输出流以便直接获取字节

            RandomAccessFile randomAccessFile = new RandomAccessFile(new File(targetFilepath), "w");
            randomAccessFile.seek(start);
            randomAccessFile.write(outputStream.toByteArray());

            ++count;
            if (count == threadNum) {
                System.out.println("远程文件异步下载耗时: " + (System.currentTimeMillis() - startTime) + "  当前线程数: " + threadNum);
            }

            outputStream.close();
            randomAccessFile.close();
            response.getEntity().getContent().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//    public void muiltDownloadRunner(String url
//            , String targetFilepath
//            , long start
//            , long end
//            , TreeMap<Long, String> fileList
//            , long size) {
//        try {
//            HttpClient httpClient = HttpClients.createDefault();
//            HttpGet httpGet = new HttpGet(url);
//            httpGet.addHeader("User-Agent", DownLoad_My_Configs.httpGet_Header);
//            httpGet.addHeader("Range", "bytes=" + start + "-" + end);
//            HttpResponse response = httpClient.execute(httpGet);
//
//            File tmpFile = FileUtil.createTempFile(new File(targetFilepath.substring(0, targetFilepath.lastIndexOf("\\"))));
//            FileUtil.writeBytes(toByteArray(response.getEntity().getContent()), tmpFile);
//
//            fileList.put(start, tmpFile.getPath());
//
//            if (fileList.values().size() == size) {
//                System.out.print("远程文件异步下载耗时: " + (System.currentTimeMillis() - startTime) + "  当前线程数: " + size);
//                long start1 = System.currentTimeMillis();
//                fileList.values().forEach(path -> {
//                    try {
//                        FileUtils.writeByteArrayToFile(new File(targetFilepath), FileUtil.readBytes(path), true);
//                        //System.out.println("开始合并文件...当前文件大小: " + new File(path).length());
//                        new File(path).delete();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                });
//                System.out.println("  远程文件合并耗时: " + (System.currentTimeMillis() - start1));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


    public byte[] toByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        MyIOUtil.inputStreamWriteToOutputStream(input, output);
        return output.toByteArray();
    }

    public Long getContentLength(String url) throws IOException {
        HttpGet httpGet = new HttpGet(url);
        HttpResponse response = httpClient.execute(httpGet);

        return Long.valueOf(Arrays.stream(response.getAllHeaders())
                .filter(d -> d.getName().equals("Content-Length"))
                .findFirst()
                .get()
                .getValue());
    }
}
