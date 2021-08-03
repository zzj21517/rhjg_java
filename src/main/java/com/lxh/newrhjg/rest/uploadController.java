package com.lxh.newrhjg.rest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.zxing.aztec.encoder.Encoder;
import com.lxh.newrhjg.api.IframeAttachinfo;
import com.lxh.newrhjg.api.Inewcommon;
import com.lxh.newrhjg.entity.FrameAttchinfo;
import com.lxh.rhjg.active.api.Icommon;
import com.lxh.rhjg.common.util.CreateMatrixToImage;
import com.lxh.rhjg.common.util.HttpClient;
import com.lxh.test.common.PropertiesUtil;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpRetryException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

@RestController
@RequestMapping("/upload")
public class uploadController {
    @Autowired
    Icommon icommon;
    @Autowired
    Inewcommon inewcommon;
    private static final Logger logger = LoggerFactory.getLogger(uploadController.class);
    @Autowired
    IframeAttachinfo attachinfo;

    @RequestMapping("/uploadPic")
    public String uploadPicture(HttpServletRequest request, HttpServletResponse response) {
        JSONObject rJsonObject = new JSONObject();
        //获取当前日志
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sDateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        String datatime = sDateFormat.format(new Date());
        String fileDate=sDateFormat1.format(new Date());
        try {
        //获取从前台传过来得图片
        MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;
        MultipartFile multipartFile = req.getFile("file");
        String fileName = multipartFile.getOriginalFilename();
        //获取文件需要上传到的路径
        String loadpath="/upload/"+fileDate;
        String path = request.getRealPath(loadpath) + "/";
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdir();
        }
        request.setCharacterEncoding("utf-8");  //设置编码
        //获得磁盘文件条目工厂
        DiskFileItemFactory factory = new DiskFileItemFactory();
        //如果没以下两行设置的话,上传大的文件会占用很多内存，
        //设置暂时存放的存储室,这个存储室可以和最终存储文件的目录不同
        /**
         * 原理: 它是先存到暂时存储室，然后再真正写到对应目录的硬盘上，
         * 按理来说当上传一个文件时，其实是上传了两份，第一个是以 .tem 格式的
         * 然后再将其真正写到对应目录的硬盘上
         */
            factory.setRepository(dir);
            //设置缓存的大小，当上传文件的容量超过该缓存时，直接放到暂时存储室
            factory.setSizeThreshold(1024 * 1024);
            //高水平的API文件上传处理
            JSONObject jsonObject = JSON.parseObject(req.getParameter("param"));
            String clinetguid = "";
            String attachguid = "";
            if (jsonObject.get("clientguid") != null)
                clinetguid = jsonObject.get("clientguid").toString();
            else
                clinetguid = UUID.randomUUID().toString();
            if (jsonObject.get("attachguid") != null)
                attachguid = jsonObject.get("attachguid").toString();
            else
                attachguid = UUID.randomUUID().toString();
            //自定义上传图片的名字为userId.jpg
            String type="";//获取附件类型
            String [] arrfile=fileName.split("\\.");
            if (arrfile.length >= 2){
              type=arrfile[arrfile.length-1];
            }
            //附件重新赋值
            fileName=attachguid+"."+type;
            String destPath = path + attachguid+"."+type;
            //真正写到磁盘上
            File file = new File(destPath);
            OutputStream out = new FileOutputStream(file);
            InputStream in = multipartFile.getInputStream();
            int length = 0;
            byte[] buf = new byte[1024];
            // in.read(buf) 每次读到的数据存放在buf 数组中
            while ((length = in.read(buf)) != -1) {
                //在buf数组中取出数据写到（输出流）磁盘上
                out.write(buf, 0, length);
            }
            in.close();
            out.close();
            //插入附件路径

            FrameAttchinfo frameAttchinfo = new FrameAttchinfo();
            frameAttchinfo.setFilename(fileName);
            if (!"".equals(clinetguid) && clinetguid != null)
                frameAttchinfo.setClientguid(clinetguid);
            else
                frameAttchinfo.setClientguid(UUID.randomUUID().toString());
            if (!"".equals(attachguid) && attachguid != null)
                frameAttchinfo.setAttachguid(attachguid);
            else
                frameAttchinfo.setAttachguid(UUID.randomUUID().toString());
            frameAttchinfo.setFilepath(request.getRequestURL().toString().split("rest")[0] +loadpath+"/" + fileName);
            frameAttchinfo.setUploadtime(datatime);
            frameAttchinfo.setFiletype(type);
            frameAttchinfo.setFilesize(String.valueOf(new Long(multipartFile.getSize()).intValue()));
            int n = attachinfo.insert(frameAttchinfo);
            if (n == 1)
                rJsonObject.put("code", "200");//
            else
                rJsonObject.put("code", "400");//
        } catch (Exception ex) {
            logger.info("", ex.getMessage());
            rJsonObject.put("code", "400");//
        }
        return rJsonObject.toJSONString();
    }
    /*
     *删除附件
     */
    @RequestMapping(value = "/delAttachinfo", method = RequestMethod.POST)
    public String delAttachinfo(@RequestBody String params) {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject = JSONObject.parseObject(jsonObject.getString("param"));
        String rowGuid = UUID.randomUUID().toString();
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datatime = sDateFormat.format(new Date());
        try{
        String attachGuid = jsonObject.get("attachGuid").toString();
        FrameAttchinfo frameAttchinfo=new FrameAttchinfo();
        frameAttchinfo.setAttachguid(attachGuid);
        attachinfo.delete(frameAttchinfo);
            rJsonObject.put("code", "200");//
        }catch (Exception e){
            rJsonObject.put("code", "400");//插入失败
        }
        return  rJsonObject.toJSONString();
    }
    /*
     * 获取附件
     */
    @RequestMapping(value = "/getAttachinfo", method = RequestMethod.POST)
    public String getAttachinfo(@RequestBody String params) {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject = JSONObject.parseObject(jsonObject.getString("param"));
        String rowGuid = UUID.randomUUID().toString();
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datatime = sDateFormat.format(new Date());
        String clientGuid = jsonObject.get("clientGuid").toString();
        Map<String,Object> map=new HashMap<>();
        map.put("clientGuid=",clientGuid);
        try{
        List<HashMap<String, Object>> list = inewcommon.findlist("frame_attachinfo", "*", map, "", "", 0, 20);
         int  count= inewcommon.findlist("frame_attachinfo", map, "");
        rJsonObject.put("code", "200");//插入失败
        rJsonObject.put("result",list);//插入失败
        rJsonObject.put("count",count);//插入失败
        }catch (Exception e){
            rJsonObject.put("code", "400");//插入失败
        }
        return  rJsonObject.toJSONString();
    }
    @RequestMapping(value = "/addQRcode", method = RequestMethod.POST)
    public String addQRcode(@RequestBody String params,HttpServletRequest request) {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject = JSONObject.parseObject(jsonObject.getString("param"));
        String uid=jsonObject.get("shortCode").toString();
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String loadpath="/upload/back.jpg";
        String path = request.getRealPath(loadpath);
        try {
            //图片加二维码
              String filepath= CreateQRCodeOnImage(path,uid);
            //加字
            Font font = new Font("宋体",Font.BOLD,32);
            addWaterMark(filepath,filepath,uid,Color.red,font);
         rJsonObject.put("code", "200");
         filepath=request.getRequestURL().toString().split("rest")[0] +"upload/"+uid+".png";
         rJsonObject.put("filepath", filepath);
        } catch (Exception ex) {
            logger.info("", ex.getMessage());
            rJsonObject.put("code", "400");
            //e.printStackTrace();
        }

        return  rJsonObject.toJSONString();
    }
    /**
     *  [在图片底部生成二维码]
     *  @param imagePath
     *  @param qrCodeContent
     *  @throws Exception
     * @exception/throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static String CreateQRCodeOnImage(String filePath,String uid) throws Exception{
        File file = new File(filePath);
      //  BufferedImage image= (BufferedImage) CreateMatrixToImage.writeQrCodeContent(qrCodeContent,250,250);//二维码
        BufferedImage image= getminiqrQr(filePath,uid);
        BufferedImage bg= ImageIO.read(file);
        Graphics2D g=bg.createGraphics();
        int width=image.getWidth(null) > bg.getWidth() * 3/10? (bg.getWidth() * 3/10) : image.getWidth(null);
        int height=image.getHeight(null) > bg.getHeight() *2/10? (bg.getHeight() *2/10) : image.getWidth(null);
        g.drawImage(image,(bg.getWidth()- width)/2+20,(bg.getHeight()-height-70),180,180,null);
        g.dispose();
        bg.flush();
        image.flush();
        SimpleDateFormat sDateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        String fileDate=sDateFormat1.format(new Date());
        //fileDate+"/"
        String path =filePath.replace("back.jpg",uid+".png");
       File  newfile = new File(path);
        if(!newfile.exists()) {
            newfile.createNewFile();
        }
        ImageIO.write(bg,"png", newfile);
        return  path;
    }
    //生成二维码
    public static BufferedImage getminiqrQr(String filepath,String uid) {
           BufferedImage image=null;
           PropertiesUtil.loadFile("encode.properties");
           //String appid = PropertiesUtil.getPropertyValue("appid");
           //String secret = PropertiesUtil.getPropertyValue("secret");
           String appid ="wx2ab6140fbbf8ac57";
           String secret ="8566079acc30dc454e7b0046b14c4355";
           String codeUrl = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=";//生成二维码url
           String url = "https://api.weixin.qq.com/cgi-bin/token?appid=" + appid + "&secret=" + secret + "&grant_type=client_credential";
           String oauthResponseText=HttpClient.doGet(url);
           com.alibaba.fastjson.JSONObject jo = com.alibaba.fastjson.JSONObject.parseObject(oauthResponseText);
           Map<String, String> resultMap = new HashMap<String, String>();
           String access_token=jo.get("access_token").toString();
           Map<String,Object> map=new HashMap<>();
        RestTemplate rest = new RestTemplate();
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            codeUrl+=access_token;
            Map<String,Object> param = new HashMap<>();
            //param.put("page", "pages/register/register?shortCode="+uid);
            param.put("page", "pages/register/register");
            //param.put("path", "pages/register/register?shortCode="+uid);
            param.put("width", 280);
            param.put("scene", "shortCodeA"+uid);
            param.put("auto_color", false);
            Map<String,Object> line_color = new HashMap<>();
            line_color.put("r", 0);
            line_color.put("g", 0);
            line_color.put("b", 0);
            param.put("line_color", line_color);
            //logger.info("调用生成微信URL接口传参:" + param);
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            HttpEntity requestEntity = new HttpEntity(param, headers);
            ResponseEntity<byte[]> entity = rest.exchange(codeUrl, HttpMethod.POST, requestEntity, byte[].class, new Object[0]);
           // logger.info("调用小程序生成微信永久小程序码URL接口返回结果:" + entity.getBody());
            byte[] result = entity.getBody();
            String S=new String(result,"UTF-8");
            inputStream = new ByteArrayInputStream(result);
            image = ImageIO.read(inputStream);
        } catch (Exception e) {
            logger.info("调用小程序生成微信永久小程序码URL接口异常",e);
        } finally {
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(outputStream != null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return image;
    }
    /**
     * @param srcImgPath 源图片路径
     * @param tarImgPath 保存的图片路径
     * @param waterMarkContent 水印内容
     * @param markContentColor 水印颜色
     * @param font 水印字体
     */
    public void addWaterMark(String srcImgPath, String tarImgPath, String waterMarkContent,Color markContentColor,Font font) {

        try {
            // 读取原图片信息
            File srcImgFile = new File(srcImgPath);//得到文件
            Image srcImg = ImageIO.read(srcImgFile);//文件转化为图片
            int srcImgWidth = srcImg.getWidth(null);//获取图片的宽
            int srcImgHeight = srcImg.getHeight(null);//获取图片的高
            // 加水印
            BufferedImage bufImg = new BufferedImage(srcImgWidth, srcImgHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bufImg.createGraphics();
            g.drawImage(srcImg, 0, 0, srcImgWidth, srcImgHeight, null);
            g.setColor(markContentColor); //根据图片的背景设置水印颜色
            g.setFont(font);              //设置字体

            //设置水印的坐标
            //int x = srcImgWidth - 2*getWatermarkLength(waterMarkContent, g);
            //int y = srcImgHeight - 2*getWatermarkLength(waterMarkContent, g);
            g.drawString("邀请码："+waterMarkContent, 290, 820);  //画出水印
            g.dispose();
            // 输出图片
            FileOutputStream outImgStream = new FileOutputStream(tarImgPath);
            ImageIO.write(bufImg, "png", outImgStream);
            outImgStream.flush();
            outImgStream.close();

        } catch (Exception e) {
            // TODO: handle exception
        }
    }
    public int getWatermarkLength(String waterMarkContent, Graphics2D g) {
        return g.getFontMetrics(g.getFont()).charsWidth(waterMarkContent.toCharArray(), 0, waterMarkContent.length());
    }

}
