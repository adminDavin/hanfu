package com.hanfu.order.center.request;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hanfu.inner.model.product.center.Product;
import com.hanfu.inner.sdk.product.center.ProductService;
import com.hanfu.order.center.manual.model.OrderInfo;

@Component
public class MyPrint implements Printable {
    @Autowired
    private ProductService productService;
    private static MyPrint myPrint;

    @PostConstruct
    //通过@PostConstruct实现初始化bean之前进行的操作
    public void init() {
        myPrint = this;
        myPrint.productService = this.productService;
    }

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    //每个条项的长度
    private float filedW = 120;
    //list里面字段的默认长度
    private float commonListItemW = 55;
    //序号
    private int dataNo = 0;
    //标题
    private String title = "出 库 单";
    //列表字段名称
    private List<String> listFieldTitles = new ArrayList<>(Arrays.asList("序号", "编号", "名称", "规格", "单位", "单价", "数量", "小计", "备注"));
    //列表字段长度
    private List<Float> listFieldWidths = new ArrayList<>(Arrays.asList(commonListItemW, commonListItemW, commonListItemW, commonListItemW, commonListItemW, commonListItemW, commonListItemW, commonListItemW, commonListItemW));
    //列表字段key 对应对象的属性值
    private List<String> listFieldKeys = new ArrayList<>(Arrays.asList("no", "code", "name", "spec", "unit", "unitPrice", "num", "total", "remark"));
    private List<Product> dataList;
    //需要打印的页数
    private int totalPageCount;

    public List<Product> getDataList() {
        return dataList;
    }

    public void setDataList(List<Product> dataList) {
        this.dataList = dataList;
    }

    public int getTotalPageCount() {
        return totalPageCount;
    }

    public void setTotalPageCount(int totalPageCount) {
        this.totalPageCount = totalPageCount;
    }

    @SuppressWarnings("null")
    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        OrderInfo orderInfo = null;
        System.out.println("pageIndex为:" + pageIndex);
        Graphics2D g2 = (Graphics2D) graphics;
        g2.setColor(Color.black);
        if (pageIndex > totalPageCount - 1) {
            return NO_SUCH_PAGE;
        } else {
            System.out.println("开始打印第:" + (pageIndex + 1) + "页");
            g2.translate(pageFormat.getImageableX(), pageFormat.getImageableY());// 转换坐标，确定打印边界

            //打印起点坐标
            double x = pageFormat.getImageableX();  //返回与此 PageFormat相关的 Paper对象的可成像区域左上方点的 x坐标。
            double y = pageFormat.getImageableY();  //返回与此 PageFormat相关的 Paper对象的可成像区域左上方点的 y坐标。

            //Font.PLAIN： 普通样式常量  Font.ITALIC 斜体样式常量 Font.BOLD 粗体样式常量。
            Font font = new Font("宋体", Font.BOLD, 18);
            g2.setFont(font);//设置标题打印字体
            g2.setStroke(new BasicStroke(1.0f));//线条宽度
            float height = font.getSize2D();//获取字体的高度

            //设置标题
            g2.drawString(title, (float) x + 180, (float) y + height);

            float line = 2 * height; //下一行开始打印的高度
            g2.setFont(new Font("宋体", Font.PLAIN, 10));
            height = font.getSize2D();// 字体高度

            line += 5;
            g2.drawString("出货日期:", (float) x, (float) y + line);
            g2.drawString("交货进间:", (float) x + filedW, (float) y + line);
            g2.drawString("出货单号:", (float) x + 2 * filedW, (float) y + line);

            line += height;
            g2.drawString("取货方式: 货到付款", (float) x, (float) y + line);
            g2.drawString("电话号码: ", (float) x + filedW, (float) y + line);
            g2.drawString("页    次: 第 1 页,共 2 页", (float) x + 2 * filedW, (float) y + line);

            line += height;
            g2.drawString("收 货 人: " + orderInfo.getRealName(), (float) x, (float) y + line);
            g2.drawString("移动电话: " + orderInfo.getPhone(), (float) x + filedW, (float) y + line);
            g2.drawString("订购客户: " + orderInfo.getRealName(), (float) x + 2 * filedW, (float) y + line);

            line += height;
            g2.drawString("收货地址: " + orderInfo.getAddress(), (float) x, (float) y + line);
            g2.drawString("出货仓库: ", (float) x + 2 * filedW, (float) y + line);

            //表格线微调值
            float lineFineTuning = 5;
            //设置列表项标题
            line += height;
            //画上横线
            g2.drawLine((int) (x - lineFineTuning), (int) (y + line - height + lineFineTuning), (int) (x + getlistFieldWidthTotal()), (int) (y + line - height + lineFineTuning));
            //画左竖线
            g2.drawLine((int) (x - lineFineTuning), (int) (y + line - height + lineFineTuning), (int) (x - lineFineTuning), (int) (y + line + lineFineTuning));
            for (int i = 0; i < listFieldTitles.size(); i++) {
                float offsetNum = 0;
                for (int j = 0; j < i; j++) {
                    offsetNum += listFieldWidths.get(j);
                }
                g2.drawString(listFieldTitles.get(i).toString(), (float) x + offsetNum, (float) y + line);
                //画竖线
                int temX = (int) (x + getlistFieldWidthTotal(i) - lineFineTuning);
                if (i == listFieldTitles.size() - 1) {
                    temX = temX + (int) lineFineTuning;
                }
                g2.drawLine(temX, (int) (y + line - height + lineFineTuning), temX, (int) (y + line + height));
            }
            //画下横线
            g2.drawLine((int) (x - lineFineTuning), (int) (y + line + lineFineTuning), (int) (x + getlistFieldWidthTotal()), (int) (y + line + lineFineTuning));

            //设置列表项数据
            line += height;
            if (dataList != null && dataList.size() > 0) {
                for (int i = 0; i < dataList.size(); i++) {
                    dataNo++;
                    for (int k = 0; k < listFieldKeys.size(); k++) {
                        float offsetNum = 0;
                        for (int j = 0; j < k; j++) {
                            offsetNum += listFieldWidths.get(j);
                        }
                        String filedValue = "";
                        //如果是序号列
                        if (k == 0) {
                            filedValue = String.valueOf(dataNo);
                        } else {
                            filedValue = getValueByKey(dataList.get(i), listFieldKeys.get(k)).toString();
                        }
                        g2.drawString(filedValue, (float) x + offsetNum, (float) y + line);
                        //画竖线
                        int temX = (int) (x + getlistFieldWidthTotal(k) - lineFineTuning);
                        if (k == listFieldTitles.size() - 1) {
                            temX = temX + (int) lineFineTuning;
                        }
                        g2.drawLine(temX, (int) (y + line - height + lineFineTuning), temX, (int) (y + line + lineFineTuning));
                        //画下横线
                        g2.drawLine((int) (x - lineFineTuning), (int) (y + line + lineFineTuning), (int) (x + getlistFieldWidthTotal()), (int) (y + line + lineFineTuning));
                    }
                    //画左竖线
                    g2.drawLine((int) (x - lineFineTuning), (int) (y + line - height + lineFineTuning), (int) (x - lineFineTuning), (int) (y + line + lineFineTuning));
                    line += height;
                }
            }

            //在点 (x1, y1) 和 (x2, y2) 之间画一条线
            //line += height;
            //g2.drawLine((int) x, (int) (y + line), (int) x + 500, (int) (y + line));

            //虚线
            //line += height;
            //g2.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 10.0f, new float[] { 2, 2 }, 0));
            //g2.drawLine((int) x, (int) (y + line), (int) x + 500, (int) (y + line));

            line += height;
            g2.drawString("备注: " + orderInfo.getHfDesc(), (float) x, (float) y + line);

            line += height;
            g2.drawString("服务电话: ", (float) x, (float) y + line);
            g2.drawString("订单编号: " + orderInfo.getOrdersId(), (float) x + filedW, (float) y + line);
            g2.drawString("制表日期:" + sdf.format(new Date()), (float) x + 2 * filedW, (float) y + line);

            return PAGE_EXISTS;
        }
    }

    private Object getValueByKey(Object obj, String key) {
        @SuppressWarnings("rawtypes")
        Class userCla = (Class) obj.getClass();
        /* 得到类中的所有属性集合 */
        Field[] fs = userCla.getDeclaredFields();
        for (int i = 0; i < fs.length; i++) {
            Field f = fs[i];
            f.setAccessible(true); // 设置些属性是可以访问的
            try {
                if (f.getName().endsWith(key)) {
                    return f.get(obj);
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        // 没有查到时返回空字符串
        return "";
    }

    private double getlistFieldWidthTotal() {
        float widthTotal = 0;
        for (int i = 0; i < listFieldWidths.size(); i++) {
            widthTotal += listFieldWidths.get(i);
        }
        return widthTotal;
    }

    private double getlistFieldWidthTotal(int index) {
        float widthTotal = 0;
        for (int i = 0; i <= index; i++) {
            widthTotal += listFieldWidths.get(i);
        }
        return widthTotal;
    }

    public void doPrint(Printable order) {
        try {
            //Book 类提供文档的表示形式，该文档的页面可以使用不同的页面格式和页面 painter
            Book book = new Book(); //要打印的文档
            //PageFormat类描述要打印的页面大小和方向
            PageFormat pf = new PageFormat();  //初始化一个页面打印对象
            pf.setOrientation(PageFormat.PORTRAIT); //设置页面打印方向，从上往下，从左往右
            //设置打印纸页面信息。通过Paper设置页面的空白边距和可打印区域。必须与实际打印纸张大小相符。
            Paper paper = new Paper();
            // 纸张大小
            //paper.setSize(683, 793.7);
            paper.setSize(683, 500);
            // A4(595 X 842)设置打印区域 A4纸的默认X,Y边距是72
            //paper.setImageableArea(20, 20, 683, 793.7);
            paper.setImageableArea(20, 20, 683, 500);
            pf.setPaper(paper);
            book.append(order, pf, this.totalPageCount);
            PrinterJob job = PrinterJob.getPrinterJob();   //获取打印服务对象
            job.setPageable(book);  //设置打印类
            job.print();
        } catch (PrinterException e) {
            e.printStackTrace();
        }
    }
}
