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
        //??????????????????
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sDateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        String datatime = sDateFormat.format(new Date());
        String fileDate=sDateFormat1.format(new Date());
        try {
        //?????????????????????????????????
        MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;
        MultipartFile multipartFile = req.getFile("file");
        String fileName = multipartFile.getOriginalFilename();
        //????????????????????????????????????
        String loadpath="/upload/"+fileDate;
        String path = request.getRealPath(loadpath) + "/";
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdir();
        }
        request.setCharacterEncoding("utf-8");  //????????????
        //??????????????????????????????
        DiskFileItemFactory factory = new DiskFileItemFactory();
        //?????????????????????????????????,??????????????????????????????????????????
        //??????????????????????????????,?????????????????????????????????????????????????????????
        /**
         * ??????: ?????????????????????????????????????????????????????????????????????????????????
         * ????????????????????????????????????????????????????????????????????????????????? .tem ?????????
         * ???????????????????????????????????????????????????
         */
            factory.setRepository(dir);
            //????????????????????????????????????????????????????????????????????????????????????????????????
            factory.setSizeThreshold(1024 * 1024);
            //????????????API??????????????????
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
            //?????????????????????????????????userId.jpg
            String type="";//??????????????????
            String [] arrfile=fileName.split("\\.");
            if (arrfile.length >= 2){
              type=arrfile[arrfile.length-1];
            }
            //??????????????????
            fileName=attachguid+"."+type;
            String destPath = path + attachguid+"."+type;
            //?????????????????????
            File file = new File(destPath);
            OutputStream out = new FileOutputStream(file);
            InputStream in = multipartFile.getInputStream();
            int length = 0;
            byte[] buf = new byte[1024];
            // in.read(buf) ??????????????????????????????buf ?????????
            while ((length = in.read(buf)) != -1) {
                //???buf???????????????????????????????????????????????????
                out.write(buf, 0, length);
            }
            in.close();
            out.close();
            //??????????????????

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
            if (n == 1){
                rJsonObject.put("url",frameAttchinfo.getFilepath());
                rJsonObject.put("code", "200");//
            }
            else
                rJsonObject.put("code", "400");//
        } catch (Exception ex) {
            logger.info("", ex.getMessage());
            rJsonObject.put("code", "400");//
        }
        return rJsonObject.toJSONString();
    }
    /*
     *????????????
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
            rJsonObject.put("code", "400");//????????????
        }
        return  rJsonObject.toJSONString();
    }

    /*
     * ??????????????????
     */
    @RequestMapping(value = "/getImgList", method = RequestMethod.POST)
    public String getImgList(@RequestBody String params) {
        JSONObject rJsonObject = new JSONObject();
        JSONObject jsonObject = JSONObject.parseObject(params);
        jsonObject = JSONObject.parseObject(jsonObject.getString("param"));
        String clientGuid = jsonObject.get("clientGuid")!=null?jsonObject.get("clientGuid").toString():"";
        Map<String,Object> map=new HashMap<>();
        map.put("clientGuid",clientGuid);
        try{
            List<FrameAttchinfo> list = attachinfo.find(map);
            rJsonObject.put("code","200");
            rJsonObject.put("imgList",list);
        }catch (Exception e){
            rJsonObject.put("code", "500");
        }
        return  rJsonObject.toJSONString();
    }
    /*
     * ????????????
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
        rJsonObject.put("code", "200");//????????????
        rJsonObject.put("result",list);//????????????
        rJsonObject.put("count",count);//????????????
        }catch (Exception e){
            rJsonObject.put("code", "400");//????????????
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
            //??????????????????
              String filepath= CreateQRCodeOnImage(path,uid);
            //??????
            Font font = new Font("??????",Font.BOLD,32);
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
     *  [??????????????????????????????]
     *  @throws Exception
     * @exception/throws [????????????] [????????????]
     * @see [?????????#????????????#??????]
     */
    public static String CreateQRCodeOnImage(String filePath,String uid) throws Exception{
        File file = new File(filePath);
      //  BufferedImage image= (BufferedImage) CreateMatrixToImage.writeQrCodeContent(qrCodeContent,250,250);//?????????
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
    //???????????????
    public static BufferedImage getminiqrQr(String filepath,String uid) {
           BufferedImage image=null;
           PropertiesUtil.loadFile("encode.properties");
           //String appid = PropertiesUtil.getPropertyValue("appid");
           //String secret = PropertiesUtil.getPropertyValue("secret");
           String appid ="wx2ab6140fbbf8ac57";
           String secret ="8566079acc30dc454e7b0046b14c4355";
           String codeUrl = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=";//???????????????url
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
            //logger.info("??????????????????URL????????????:" + param);
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            HttpEntity requestEntity = new HttpEntity(param, headers);
            ResponseEntity<byte[]> entity = rest.exchange(codeUrl, HttpMethod.POST, requestEntity, byte[].class, new Object[0]);
           // logger.info("?????????????????????????????????????????????URL??????????????????:" + entity.getBody());
            byte[] result = entity.getBody();
            String S=new String(result,"UTF-8");
            inputStream = new ByteArrayInputStream(result);
            image = ImageIO.read(inputStream);
        } catch (Exception e) {
            logger.info("?????????????????????????????????????????????URL????????????",e);
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
     * @param srcImgPath ???????????????
     * @param tarImgPath ?????????????????????
     * @param waterMarkContent ????????????
     * @param markContentColor ????????????
     * @param font ????????????
     */
    public void addWaterMark(String srcImgPath, String tarImgPath, String waterMarkContent,Color markContentColor,Font font) {

        try {
            // ?????????????????????
            File srcImgFile = new File(srcImgPath);//????????????
            Image srcImg = ImageIO.read(srcImgFile);//?????????????????????
            int srcImgWidth = srcImg.getWidth(null);//??????????????????
            int srcImgHeight = srcImg.getHeight(null);//??????????????????
            // ?????????
            BufferedImage bufImg = new BufferedImage(srcImgWidth, srcImgHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bufImg.createGraphics();
            g.drawImage(srcImg, 0, 0, srcImgWidth, srcImgHeight, null);
            g.setColor(markContentColor); //???????????????????????????????????????
            g.setFont(font);              //????????????

            //?????????????????????
            //int x = srcImgWidth - 2*getWatermarkLength(waterMarkContent, g);
            //int y = srcImgHeight - 2*getWatermarkLength(waterMarkContent, g);
            g.drawString("????????????"+waterMarkContent, 290, 820);  //????????????
            g.dispose();
            // ????????????
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
