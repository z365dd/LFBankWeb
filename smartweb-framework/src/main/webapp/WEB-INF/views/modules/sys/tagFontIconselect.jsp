<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>字体图标选择</title>
	<meta name="decorator" content="blank"/>
    <style type="text/css">
    	.page-header {clear:both;margin:0 20px;padding-top:20px;}
		.the-icons {padding:25px 10px 15px;list-style:none;}
		.the-icons li {float:left;width:48%;line-height:25px;margin:2px 5px;cursor:pointer;}
		.the-icons i {margin:1px 5px;font-size:16px;} .the-icons li:hover {background-color:#efefef;}
        .the-icons li.active {background-color:#0088CC;color:#ffffff;}
        .the-icons li:hover i{font-size:20px;}
    </style>
    <script type="text/javascript">
	    $(document).ready(function(){
	    	$("#icons li").click(function(){
	    		$("#icons li").removeClass("active");
	    		$("#icons li i").removeClass("icon-white");
	    		$(this).addClass("active");
	    		$(this).children("i").addClass("icon-white");
	    		$("#icon").val($(this).text());
	    	});
	    	$("#icons li").each(function(){
	    		if ($(this).text()=="${value}"){
	    			$(this).click();
	    		}
	    	});
	    	$("#icons li").dblclick(function(){
	    		top.$.jBox.getBox().find("button[value='ok']").trigger("click");
	    	});
	    });
    </script>
</head>
<body>
<input type="hidden" id="icon" value="${value}" />
<div id="icons">
		
	    <h2 class="iconfont page-header"> 字体图标</h2>
	    
	    <ul class="iconfont the-icons">
	    	<li>
                <i class="icon iconfont icon-qudaoguanli"></i>
                    icon-qudaoguanli
                </li>
            
                <li>
                <i class="icon iconfont icon-tuiguangqudao"></i>
                    icon-tuiguangqudao
                </li>
            
                <li>
                <i class="icon iconfont icon-back"></i>
                    icon-back
                </li>
            
                <li>
                <i class="icon iconfont icon-close"></i>
                    icon-close
                </li>
            
                <li>
                <i class="icon iconfont icon-delete"></i>
                    icon-delete
                </li>
            
                <li>
                <i class="icon iconfont icon-less"></i>
                    icon-less
                </li>
            
                <li>
                <i class="icon iconfont icon-moreunfold"></i>
                    icon-moreunfold
                </li>
            
                <li>
                <i class="icon iconfont icon-more"></i>
                    icon-more
                </li>
            
                <li>
                <i class="icon iconfont icon-pic"></i>
                    icon-pic
                </li>
            
                <li>
                <i class="icon iconfont icon-selected"></i>
                    icon-selected
                </li>
            
                <li>
                <i class="icon iconfont icon-set"></i>
                    icon-set
                </li>
            
                <li>
                <i class="icon iconfont icon-cansaiduixiangren"></i>
                    icon-cansaiduixiangren
                </li>
            
                <li>
                <i class="icon iconfont icon-qudaoqushiduibifenxi"></i>
                    icon-qudaoqushiduibifenxi
                </li>
            
                <li>
                <i class="icon iconfont icon-tousuzidian"></i>
                    icon-tousuzidian
                </li>
            
                <li>
                <i class="icon iconfont icon-shujuzidian"></i>
                    icon-shujuzidian
                </li>
            
                <li>
                <i class="icon iconfont icon-duoqudaokechengfuwu"></i>
                    icon-duoqudaokechengfuwu
                </li>
            
                <li>
                <i class="icon iconfont icon-peixunyujiaoliu"></i>
                    icon-peixunyujiaoliu
                </li>
            
                <li>
                <i class="icon iconfont icon-zidian"></i>
                    icon-zidian
                </li>
            
                <li>
                <i class="icon iconfont icon-zidianxinxipingtai"></i>
                    icon-zidianxinxipingtai
                </li>
            
                <li>
                <i class="icon iconfont icon-cidiandictionary"></i>
                    icon-cidiandictionary
                </li>
            
                <li>
                <i class="icon iconfont icon-aixin"></i>
                    icon-aixin
                </li>
            
                <li>
                <i class="icon iconfont icon-changguizidian"></i>
                    icon-changguizidian
                </li>
            
                <li>
                <i class="icon iconfont icon-jipiaojingtaizidian"></i>
                    icon-jipiaojingtaizidian
                </li>
            
                <li>
                <i class="icon iconfont icon-icon"></i>
                    icon-icon
                </li>
            
                <li>
                <i class="icon iconfont icon-icon1"></i>
                    icon-icon1
                </li>
            
                <li>
                <i class="icon iconfont icon-changguizidian1"></i>
                    icon-changguizidian1
                </li>
            
                <li>
                <i class="icon iconfont icon-jipiaojingtaizidian1"></i>
                    icon-jipiaojingtaizidian1
                </li>
            
                <li>
                <i class="icon iconfont icon-kehucanshupeizhi"></i>
                    icon-kehucanshupeizhi
                </li>
            
                <li>
                <i class="icon iconfont icon-canyu"></i>
                    icon-canyu
                </li>
            
                <li>
                <i class="icon iconfont icon-shezhicanyujiaoshi"></i>
                    icon-shezhicanyujiaoshi
                </li>
            
                <li>
                <i class="icon iconfont icon-xin"></i>
                    icon-xin
                </li>
            
                <li>
                <i class="icon iconfont icon-zidian1"></i>
                    icon-zidian1
                </li>
            
                <li>
                <i class="icon iconfont icon-yunongtongxiugaiqudaomima"></i>
                    icon-yunongtongxiugaiqudaomima
                </li>
            
                <li>
                <i class="icon iconfont icon-wode"></i>
                    icon-wode
                </li>
            
                <li>
                <i class="icon iconfont icon-shouye"></i>
                    icon-shouye
                </li>
            
                <li>
                <i class="icon iconfont icon-goutong"></i>
                    icon-goutong
                </li>
            
                <li>
                <i class="icon iconfont icon-tongji"></i>
                    icon-tongji
                </li>
            
                <li>
                <i class="icon iconfont icon-kehutongjishitu"></i>
                    icon-kehutongjishitu
                </li>
            
                <li>
                <i class="icon iconfont icon-licaiguihua"></i>
                    icon-licaiguihua
                </li>
            
                <li>
                <i class="icon iconfont icon-touzizuhe"></i>
                    icon-touzizuhe
                </li>
            
                <li>
                <i class="icon iconfont icon-yuyuefuwu"></i>
                    icon-yuyuefuwu
                </li>
            
                <li>
                <i class="icon iconfont icon-wodekehu"></i>
                    icon-wodekehu
                </li>
            
                <li>
                <i class="icon iconfont icon-tiqianhuankuanjisuanqi"></i>
                    icon-tiqianhuankuanjisuanqi
                </li>
            
                <li>
                <i class="icon iconfont icon-daikuanjisuanqi"></i>
                    icon-daikuanjisuanqi
                </li>
            
                <li>
                <i class="icon iconfont icon-cunkuanlixi"></i>
                    icon-cunkuanlixi
                </li>
            
                <li>
                <i class="icon iconfont icon-licaijisuanqi"></i>
                    icon-licaijisuanqi
                </li>
            
                <li>
                <i class="icon iconfont icon-waihuixingqingzixun"></i>
                    icon-waihuixingqingzixun
                </li>
            
                <li>
                <i class="icon iconfont icon-baoxianchanpin"></i>
                    icon-baoxianchanpin
                </li>
            
                <li>
                <i class="icon iconfont icon-jijinchanpinzixun"></i>
                    icon-jijinchanpinzixun
                </li>
            
                <li>
                <i class="icon iconfont icon-zhengquanxingqing"></i>
                    icon-zhengquanxingqing
                </li>
            
                <li>
                <i class="icon iconfont icon-yingxiaoshangjilei"></i>
                    icon-yingxiaoshangjilei
                </li>
            
                <li>
                <i class="icon iconfont icon-yuyuedengjilei"></i>
                    icon-yuyuedengjilei
                </li>
            
                <li>
                <i class="icon iconfont icon-kehudingdan"></i>
                    icon-kehudingdan
                </li>
            
                <li>
                <i class="icon iconfont icon-jinggaolei"></i>
                    icon-jinggaolei
                </li>
            
                <li>
                <i class="icon iconfont icon-morentubiao"></i>
                    icon-morentubiao
                </li>
            
                <li>
                <i class="icon iconfont icon-qiehuanzhanghao"></i>
                    icon-qiehuanzhanghao
                </li>
            
                <li>
                <i class="icon iconfont icon-guozhai"></i>
                    icon-guozhai
                </li>
            
                <li>
                <i class="icon iconfont icon-kaihu"></i>
                    icon-kaihu
                </li>
            
                <li>
                <i class="icon iconfont icon-kaiqia"></i>
                    icon-kaiqia
                </li>
            
                <li>
                <i class="icon iconfont icon-qianyue"></i>
                    icon-qianyue
                </li>
            
                <li>
                <i class="icon iconfont icon-shiwuguijinshu"></i>
                    icon-shiwuguijinshu
                </li>
            
                <li>
                <i class="icon iconfont icon-wodeyuyue"></i>
                    icon-wodeyuyue
                </li>
            
                <li>
                <i class="icon iconfont icon-woyaoyuyue"></i>
                    icon-woyaoyuyue
                </li>
            
                <li>
                <i class="icon iconfont icon-yanglaoguihua"></i>
                    icon-yanglaoguihua
                </li>
            
                <li>
                <i class="icon iconfont icon-yue"></i>
                    icon-yue
                </li>
            
                <li>
                <i class="icon iconfont icon-zinvjiaoyu"></i>
                    icon-zinvjiaoyu
                </li>
            
                <li>
                <i class="icon iconfont icon-chanpin"></i>
                    icon-chanpin
                </li>
            
                <li>
                <i class="icon iconfont icon-chugui"></i>
                    icon-chugui
                </li>
            
                <li>
                <i class="icon iconfont icon-duigong"></i>
                    icon-duigong
                </li>
            
                <li>
                <i class="icon iconfont icon-duisi"></i>
                    icon-duisi
                </li>
            
                <li>
                <i class="icon iconfont icon-duizhangzhongxin"></i>
                    icon-duizhangzhongxin
                </li>
            
                <li>
                <i class="icon iconfont icon-gangweiqiehuan"></i>
                    icon-gangweiqiehuan
                </li>
            
                <li>
                <i class="icon iconfont icon-jieshushijian"></i>
                    icon-jieshushijian
                </li>
            
                <li>
                <i class="icon iconfont icon-jinbi"></i>
                    icon-jinbi
                </li>
            
                <li>
                <i class="icon iconfont icon-jine01"></i>
                    icon-jine01
                </li>
            
                <li>
                <i class="icon iconfont icon-kaishishijian"></i>
                    icon-kaishishijian
                </li>
            
                <li>
                <i class="icon iconfont icon-mubiaorenwu"></i>
                    icon-mubiaorenwu
                </li>
            
                <li>
                <i class="icon iconfont icon-remen"></i>
                    icon-remen
                </li>
            
                <li>
                <i class="icon iconfont icon-renwumingcheng"></i>
                    icon-renwumingcheng
                </li>
            
                <li>
                <i class="icon iconfont icon-riqi"></i>
                    icon-riqi
                </li>
            
                <li>
                <i class="icon iconfont icon-shuju"></i>
                    icon-shuju
                </li>
            
                <li>
                <i class="icon iconfont icon-suopingmima"></i>
                    icon-suopingmima
                </li>
            
                <li>
                <i class="icon iconfont icon-tuijianren"></i>
                    icon-tuijianren
                </li>
            
                <li>
                <i class="icon iconfont icon-wanchengdu"></i>
                    icon-wanchengdu
                </li>
            
                <li>
                <i class="icon iconfont icon-xinyongqia"></i>
                    icon-xinyongqia
                </li>
            
                <li>
                <i class="icon iconfont icon-yiwancheng"></i>
                    icon-yiwancheng
                </li>
            
                <li>
                <i class="icon iconfont icon-yingxiao"></i>
                    icon-yingxiao
                </li>
            
                <li>
                <i class="icon iconfont icon-zhibiao"></i>
                    icon-zhibiao
                </li>
            
                <li>
                <i class="icon iconfont icon-zhufang"></i>
                    icon-zhufang
                </li>
            
                <li>
                <i class="icon iconfont icon-zhuxiao1"></i>
                    icon-zhuxiao1
                </li>
            
                <li>
                <i class="icon iconfont icon-dibiao2"></i>
                    icon-dibiao2
                </li>
            
                <li>
                <i class="icon iconfont icon-sousuo"></i>
                    icon-sousuo
                </li>
            
                <li>
                <i class="icon iconfont icon-zidian2"></i>
                    icon-zidian2
                </li>
            
                <li>
                <i class="icon iconfont icon-zhuxiaoxiantiao"></i>
                    icon-zhuxiaoxiantiao
                </li>
            
                <li>
                <i class="icon iconfont icon-zhishu"></i>
                    icon-zhishu
                </li>
            
                <li>
                <i class="icon iconfont icon-canyu1"></i>
                    icon-canyu1
                </li>
            
                <li>
                <i class="icon iconfont icon-zygl"></i>
                    icon-zygl
                </li>
            
                <li>
                <i class="icon iconfont icon-yuangongqudaoAPP-fuxuankuangweixuanzhong"></i>
                    icon-yuangongqudaoAPP-fuxuankuangweixuanzhong
                </li>
            
                <li>
                <i class="icon iconfont icon-yuangongqudaoAPP-fuxuankuangxuanzhong"></i>
                    icon-yuangongqudaoAPP-fuxuankuangxuanzhong
                </li>
            
                <li>
                <i class="icon iconfont icon-canyuqingkuang"></i>
                    icon-canyuqingkuang
                </li>
            
                <li>
                <i class="icon iconfont icon-canyuguanli"></i>
                    icon-canyuguanli
                </li>
            
                <li>
                <i class="icon iconfont icon-zidian3"></i>
                    icon-zidian3
                </li>
            
                <li>
                <i class="icon iconfont icon-dictionary"></i>
                    icon-dictionary
                </li>
            
                <li>
                <i class="icon iconfont icon-favorites"></i>
                    icon-favorites
                </li>
            
                <li>
                <i class="icon iconfont icon-qudaoguanli1"></i>
                    icon-qudaoguanli1
                </li>
            
                <li>
                <i class="icon iconfont icon-qudaobofang"></i>
                    icon-qudaobofang
                </li>
            
                <li>
                <i class="icon iconfont icon-channel"></i>
                    icon-channel
                </li>
            
                <li>
                <i class="icon iconfont icon-jingdianyibiaoban"></i>
                    icon-jingdianyibiaoban
                </li>
            
                <li>
                <i class="icon iconfont icon-xinzengyugengxinkehudangan"></i>
                    icon-xinzengyugengxinkehudangan
                </li>
            
                <li>
                <i class="icon iconfont icon-jingdianwanfa"></i>
                    icon-jingdianwanfa
                </li>
            
                <li>
                <i class="icon iconfont icon-jingdianwanfa1"></i>
                    icon-jingdianwanfa1
                </li>
            
                <li>
                <i class="icon iconfont icon-qudao"></i>
                    icon-qudao
                </li>
            
                <li>
                <i class="icon iconfont icon-zidian4"></i>
                    icon-zidian4
                </li>
            
                <li>
                <i class="icon iconfont icon-zidiantingyong"></i>
                    icon-zidiantingyong
                </li>
            
                <li>
                <i class="icon iconfont icon-zidianzuofei"></i>
                    icon-zidianzuofei
                </li>
            
                <li>
                <i class="icon iconfont icon-favorites1"></i>
                    icon-favorites1
                </li>
            
                <li>
                <i class="icon iconfont icon-jichuzidianziliao"></i>
                    icon-jichuzidianziliao
                </li>
            
                <li>
                <i class="icon iconfont icon-zidianguanli"></i>
                    icon-zidianguanli
                </li>
            
                <li>
                <i class="icon iconfont icon-qudao1"></i>
                    icon-qudao1
                </li>
            
                <li>
                <i class="icon iconfont icon-qudaozuzhi"></i>
                    icon-qudaozuzhi
                </li>
            
                <li>
                <i class="icon iconfont icon-yanjizhushou-shangchuan_goumaiqudao"></i>
                icon-yanjizhushou-shangchuan_goumaiqudao
                </li>
            
                <li>
                <i class="icon iconfont icon-zidianpeizhi"></i>
                icon-zidianpeizhi
                </li>
	    </ul>

	<br/><br/>
</div>
</body>