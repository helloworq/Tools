package com.zlutil.tools.toolpackage.JavaBasic;

import java.io.IOException;


public class index {
    public static void main(String[] args) throws Exception {
        String a= "\"";
        System.out.println(a);
    }
}
/*
        TreeNode head=new TreeNode();
        TreeUntis treeUntis=new TreeUntis();
        treeUntis.initTree(head,4);
        treeUntis.readTreeOrderable(head);
        System.out.println("\n");
        treeUntis.readTree(head);

        //赋值
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(5, "", "10岁");
        dataset.addValue(6, "", "20岁");
        dataset.addValue(4, "", "30岁");
        dataset.addValue(2, "", "40岁");
        List<Integer> list1=new ArrayList<>();
        List<String> list2=new ArrayList<>();
        new index().radar(dataset);

        connectPrinter c=new connectPrinter(
                "C:\\Users\\12733\\Desktop\\A.docx",
                connectPrinter.A3,
                connectPrinter.horizontal);
        c.print();


        PDDocument document =null;
        //创建文件对象
        File file=new File("C:\\Users\\12733\\Desktop\\1 - 副本OfPdfType.pdf");
        //查找文件
        document = PDDocument.load(file);
        int numbers=document.getNumberOfPages();
        System.out.println("页面数量:"+numbers);
        System.out.println(System.getProperty("java.library.path"));
        //document.getPage(1).setRotation(180);
        //document.save("C:\\Users\\12733\\Desktop\\test.pdf");


         *//*

         */
/*
        //新建打印机任务
        //document.getNumberOfPages();
        //document.getPage(1);
        PrinterJob printJob = PrinterJob.getPrinterJob();
        printJob.setJobName("PDF文件打印");
        //查找本地打印机服务
        PrintService printService = PrintServiceLookup.lookupDefaultPrintService();
        if (printService != null) {
            printJob.setPrintService(printService);
        } else {
            System.out.print("打印失败，未找到默认打印机，请检查。");
            return;
        }
        //设置纸张及缩放
        PDFPrintable pdfPrintable = new PDFPrintable(document, Scaling.SCALE_TO_FIT);
        //设置多页打印
        Book book = new Book();
        PageFormat pageFormat = new PageFormat();
        //设置打印方向
        pageFormat.setOrientation(0);//纵向
        pageFormat.setPaper(getPaper());//设置纸张
        book.append(pdfPrintable, pageFormat, document.getNumberOfPages());
        printJob.setPageable(book);
        printJob.setCopies(1);//设置打印份数
        //添加打印属性
        HashPrintRequestAttributeSet pars = new HashPrintRequestAttributeSet();
        pars.add(Sides.DUPLEX); //设置单双页
        printJob.print(pars);
*//*




        //System.out.println(jsonArray.get(0).toString().split("\"")[20]);
        //for (int i = 0; i < 3; i++) {
        //    for (int j = 0; j < jsonArray.get(i).toString().split("\"").length; j++) {
        //        System.out.println(j+"---"+jsonArray.get(i).toString().split("\"")[j]);
        //    }
        //    System.out.println("========================================");
        //}

//connectPrinter.wToPdfChange("C:\\Users\\12733\\Desktop\\1 - 副本.doc");
*/
/*
        String path="C:\\Users\\12733\\Desktop\\1 - 副本.doc";
        System.out.println("开始打印");
        ComThread.InitSTA();
        ActiveXComponent word=new ActiveXComponent("Word.Application");
        Dispatch doc=null;
        Dispatch.put(word, "Visible", new Variant(false));
        Dispatch docs=word.getProperty("Documents").toDispatch();
        doc=Dispatch.call(docs, "Open", path).toDispatch();

        try {
            Dispatch.call(doc, "PrintOut");//打印
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("打印失败");
        }finally{
            try {
                if(doc!=null){
                    Dispatch.call(doc, "Close",new Variant(0));
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            //释放资源
            ComThread.Release();
        }

*/

//        String originFilePath = "C:\\Users\\12733\\AppData\\Local\\Packages\\Microsoft.Windows.ContentDeliveryManager_cw5n1h2txyewy\\LocalState\\Assets";
//        //小程序，从Windows聚焦的目录里提取所有文件到指定目录下，并且加上.jpg后缀
//        String TargetfilePath = "C:\\Users\\12733\\Desktop\\Windows聚焦图片";
//        File file = new File(originFilePath);
//        int order = 0;//完成序号
//        //获取目录下所有文件的文件名
//        String[] listOfFile = file.list();
//        for (String s : listOfFile) {
//            //renameTo方法会把源文件夹文件删除，故不采用
//            String Targetpath = originFilePath + "\\" + s;
//            InputStream oringDataInputStream = new DataInputStream(new FileInputStream(Targetpath));
//            //全部加上.jpg后缀
//            File Targetfile = new File(TargetfilePath + "\\" + s + ".jpg");
//            if (!Targetfile.exists()) {
//                Targetfile.createNewFile();
//            }
//            OutputStream outputStream = new FileOutputStream(Targetfile);
//            inputStreamWriteToOutputStream(oringDataInputStream, outputStream);
//            System.out.println("已完成第   " + ++order + "   个文件");
//        }
//    }






//
//    List<String> getListNameFomePath(String filePath) {
//        List<String> list = new ArrayList<>();
//        File file = new File(filePath);
//        String[] listOfFile = file.list();
//        //全部加上.jpg后缀
//        for (String s : listOfFile) {
//            File subfile = new File(filePath + "\\" + s);
//            subfile.renameTo(new File(filePath + "\\" + s + ".jpg"));
//        }
//        return list;
//    }


    /**
     * 此方法用来实现将输入流中的数据写入到输出流中
     *
     * @param input
     * @param output
     * @throws IOException
     */
//    public static void inputStreamWriteToOutputStream(InputStream input, OutputStream output) throws IOException {
//        int index;
//        byte[] bytes = new byte[1024];
//        while ((index = input.read(bytes)) != -1) {
//            output.write(bytes, 0, index);
//            output.flush();
//        }
//        input.close();
//        output.close();
//    }



/*

    public static Paper getPaper() {
        //1191*842 A3
        //横向，纵向打印
        Paper paper = new Paper();
        // 默认为A4纸张，对应像素宽和高分别为 595, 842
        // 设置边距，单位是像素，10mm边距，对应 28px
        int marginLeft = 5;
        int marginRight = 0;
        int marginTop = 5;
        int marginBottom = 0;
        paper.setSize(842,1191);
        // 下面一行代码，解决了打印内容为空的问题
        paper.setImageableArea(marginLeft, marginRight, 842 - (marginLeft + marginRight), 1191 - (marginTop + marginBottom));
        return paper;
    }










    public static DefaultCategoryDataset setdata(List<Integer> listValue,List<String> listColumnKey){
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for(int i=0;i<listValue.size();i++){
            dataset.addValue(listValue.get(i),"",listColumnKey.get(i));
        }
        return dataset;
    }




     * 雷达图赋值就是定义一个dataset往里面填数据就行了，value决定了在坐标轴上的长度，columnkey决定
     * 坐标的说明 rowkey不赋值
     *
     * @param dataset
     * @throws IOException


    public void radar(DefaultCategoryDataset dataset) throws IOException {
        //传入背景照片
        Image image=ImageIO.read(new FileInputStream("C:\\Users\\12733\\Desktop\\blue.jpg"));
        //将数据集转换为雷达图
        SpiderWebPlot spiderwebplot = new SpiderWebPlot(dataset);
        //此处可操作雷达图
        if(dataset.getColumnCount()==4)
            spiderwebplot.setStartAngle(45);//设置旋转角度
        else if(dataset.getColumnCount()==6)
            spiderwebplot.setStartAngle(30);//设置旋转角度
        //spiderwebplot.draw();
        spiderwebplot.setNoDataMessage("暂无数据");
        //设置线条
        spiderwebplot.setAxisLineStroke(new BasicStroke(2.f));
        //设置图表边框不可见
        spiderwebplot.setOutlineVisible(false);
        //设置图表的背景色
        spiderwebplot.setBackgroundImage(image);
        //传给JFreeChart雷达图对象
        JFreeChart jfreechart = new JFreeChart("", TextTitle.DEFAULT_FONT,spiderwebplot, false);
        //设置背景底座颜色
        jfreechart.setBackgroundImage(image);
        //创建输出流
        FileOutputStream out = new FileOutputStream(new File("C:\\Users\\12733\\Desktop\\aaa.jpeg"));
        //写入到图片里
        ChartUtilities.writeChartAsJPEG(out, 1f,jfreechart, 500, 500,null);
        out.close();
        System.out.println("Ss");
    }







/*



    public static void BAR() throws IOException{
        CategoryDataset ds = getDataSet();
        JFreeChart chart = ChartFactory.createBarChart3D(
                "水果产量图", //图表标题
                "水果", //目录轴的显示标签
                "产量", //数值轴的显示标签
                ds, //数据集
                PlotOrientation.VERTICAL, //图表方向
                true, //是否显示图例，对于简单的柱状图必须为false
                false, //是否生成提示工具
                false);         //是否生成url链接

        CategoryPlot categoryplot = (CategoryPlot) chart.getPlot();

        NumberAxis numberaxis = (NumberAxis) categoryplot.getRangeAxis();

        CategoryAxis domainAxis = categoryplot.getDomainAxis();


        domainAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 11));


        domainAxis.setLabelFont(new Font("宋体", Font.PLAIN, 12));


        numberaxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 12));


        numberaxis.setLabelFont(new Font("黑体", Font.PLAIN, 12));


        chart.getLegend().setItemFont(new Font("宋体", Font.PLAIN, 12));


        chart.getTitle().setFont(new Font("宋体", Font.PLAIN, 12));

 *//*

 */
/*
        FileOutputStream out = null;
        try {
            out = new FileOutputStream("C:\\Users\\12733\\Desktop\\ccc.jpg");
            ChartUtilities.writeChartAsJPEG(out, 1f, chart, 400, 500, null);
        } finally {
            try {
                out.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    *//*

 */
/*
    private static CategoryDataset getDataSet() {
        DefaultCategoryDataset ds = new DefaultCategoryDataset();
        ds.addValue(100, "北京", "苹果");
        ds.addValue(100, "上海", "苹果");
        ds.addValue(100, "广州", "苹果");
        ds.addValue(200, "北京", "梨子");
        ds.addValue(200, "上海", "梨子");
        ds.addValue(200, "广州", "梨子");
        ds.addValue(300, "北京", "葡萄");
        ds.addValue(300, "上海", "葡萄");
        ds.addValue(300, "广州", "葡萄");
        ds.addValue(400, "北京", "橘子");
        ds.addValue(400, "上海", "橘子");
        ds.addValue(400, "广州", "橘子");
        ds.addValue(500, "北京", "香蕉");
        ds.addValue(500, "上海", "香蕉");
        ds.addValue(500, "广州", "香蕉");
        return ds;
    }
    *//*

 */
/*


    public static void pie() throws IOException{
        //定义默认数据集
        DefaultPieDataset dataset=new DefaultPieDataset();

        dataset.setValue("中国", 44.4);
        dataset.setValue("美国", 25.5);
        dataset.setValue("日本", 18.5);
        dataset.setValue("刚果", 4.6);
        dataset.setValue("英国", 5.5);
        dataset.setValue("其他", 0.5);
        //PiePlot3D,设置具体的饼
        PiePlot piePlot=new PiePlot(dataset);
        piePlot.setCircular(true);
        piePlot.setNoDataMessage("无数据");

        //设置图片链接
        JFreeChart jfreechart = new JFreeChart("", TextTitle.DEFAULT_FONT,piePlot, false);
        //创建输出流
        FileOutputStream out = new FileOutputStream(new File("C:\\Users\\12733\\Desktop\\bbb.jpeg"));
        //写入到图片里
        ChartUtilities.writeChartAsJPEG(out, 1f,jfreechart, 400, 500,null);
        out.close();
    }

    *//*





























































 */
/**
 * public static String generateEChart(String options, String imgName) {
 * //String dataPath = writeFile(options);
 * String path = "C:\\Users\\12733\\Desktop\\" + imgName;
 * try {
 * File file = new File(path);     //文件路径（路径+文件名）
 * if (!file.exists()) {   //文件不存在则创建文件，先创建目录
 * File dir = new File(file.getParent());
 * dir.mkdirs();
 * file.createNewFile();
 * }
 * //echarts-convert.js 脚本所在 path
 * String JSpath = "C:\\Users\\12733\\Desktop\\phantomjs-2.1.1-windows\\echarts-convert\\echarts-convert.js";
 * String cmd = "phantomjs " + JSpath + " -options " + options + " -outfile " + path + " -width 1680 -height 500";
 * Process process = Runtime.getRuntime().exec(cmd);
 * BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
 * String line = "";
 * while ((line = input.readLine()) != null) {
 * System.out.println(line);
 * }
 * input.close();
 * } catch (Exception e) {
 * e.printStackTrace();
 * } finally {
 * return path;
 * }
 * }
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * static int countCharInString(String str,char ch){
 * int times=0;
 * char[] chars=str.toCharArray();
 * for (char c:chars) {
 * if(c==ch)
 * times++;
 * }
 * return times;
 * }
 * }
 *//*







 */
