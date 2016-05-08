package com.yard.manager.modules.util;

import com.yard.manager.base.util.HttpUtils;

/**
 * 邮件模板
 * @author : xiaym
 * @date : 2015年8月14日 上午11:34:43
 * @version : 1.0
 */
public class MailTemplate {
	/**
	 * 开店验证通过邮件主题
	 */
	public static String vali_success_train_subject = "恭喜您，您的开店申请已经通过啦！";
	
	/**
	 * 开店验证通过邮件内容
	 * @return
	 */
	public static String getTrainSuccessHtml(String email) {
		StringBuffer content = new StringBuffer();
		content.append("<style type=\"text/css\">	.qmbox .hLNQXMx {position: absolute;left: -4488px;top: -3186px;	}	");
		content.append("p {	  display: block;	  -webkit-margin-before: 1em;	  -webkit-margin-after: 1em;	  -webkit-margin-start: 0px;");
		content.append("	  -webkit-margin-end: 0px;	}	.body a{text-decoration:none;}a, a:link, a:visited, a:active {text-decoration:none;}");
		content.append("a:hover {text-decoration:underline;}</style><table style=\"MARGIN: 25px auto\" cellspacing=\"0\" cellpadding=\"0\"	");
		content.append("width=\"648\" align=\"center\" border=\"0\">	<tbody><tr><td	style=\"BORDER-RIGHT: #d1ffd1 1px solid; PADDING-RIGHT: 20px; ");
		content.append("BORDER-TOP: #40aa53 5px solid; PADDING-LEFT: 20px; BACKGROUND: #ffffff; PADDING-BOTTOM: 0px; BORDER-LEFT: #d1ffd1 1px solid; ");
		content.append("PADDING-TOP: 20px\"><div><center>	<img src=\"${httpUrl}image/admin/options/peo.png\" width=\"70px\"/>	<div style=\"color:#fcc085; ");
		content.append("font-size:25px; font-weight:500;margin-top:30px;\">恭喜您！申请成功！</div>	<div style=\"margin-top:30px; font-size:14px; font-family:微软雅黑;\">");
		content.append("<p>你的开店申请已经审核通过。你的账号信息如下:</p><p>登陆账号：<span style=\"color:green\">${shoperDefaultUserName}</span> 初始密码：<span style=\"color:green\">");
		content.append("${shoperDefaultPwd}</span></p><p>邻聚商家后台管理系统登陆地址:<br/><a href=\"${shoperManagerUrlHref}\">${shoperManagerUrl}</a></p><br/><br/><p>");
		content.append("成都大院子科技有限公司  &nbsp;&nbsp; ${ctyTel}</p>	</div></center><p>	<span style=\"LINE-HEIGHT: 1.5\"><br></span></p></div>	");
		content.append("<div style=\"PADDING-TOP: 15px\"><br></div></td></tr><tr><td	style=\"PADDING-RIGHT: 0px; PADDING-LEFT: 0px; PADDING-BOTTOM: 35px; ");
		content.append("PADDING-TOP: 35px; BORDER-BOTTOM: #999 2px solid\"	align=\"left\">	<div style=\"FONT-FAMILY: 微软雅黑, Arial\"><div style=\"FONT-SIZE: 16px; ");
		content.append("COLOR: #999; LINE-HEIGHT: 1.6\"><span style=\"COLOR: rgb(255, 255, 255)\">yyy</span></div>	</div></td></tr><tr><td style=\"FONT-FAMILY: 微软雅黑, Arial\" ");
		content.append("align=\"left\">	<divstyle=\"FONT-SIZE: 14px; COLOR: #999; LINE-HEIGHT: 1.4; PADDING-TOP: 25px\"><div style=\"PADDING-TOP: 8px\"><br></div>	</div></td></tr>");
		content.append("	</tbody></table>");
		
		return content.toString().replace("${httpUrl}", HttpUtils.ServerBlackUrl)
								 .replace("${shoperDefaultUserName}", email)
								 .replace("${shoperDefaultPwd}", PropertiesFactory.getCONFIG_TRAIN_EMAIL_shoperDefaultPwd())
								 .replace("${shoperManagerUrl}", PropertiesFactory.getCONFIG_TRAIN_EMAIL_shoperManagerUrl())
								 .replace("${shoperManagerUrlHref}", PropertiesFactory.getCONFIG_TRAIN_EMAIL_shoperManagerUrl())
								 .replace("${ctyTel}", PropertiesFactory.getCONFIG_TRAIN_EMAIL_ctyTel());
	}
	
}
