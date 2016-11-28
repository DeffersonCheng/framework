package com.wdl.util.pdf.pojo;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import org.springframework.web.context.ContextLoader;

import freemarker.core.Environment;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

public class ImageTag implements TemplateDirectiveModel {
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
			throws TemplateException, IOException {
		SimpleScalar id = (SimpleScalar) params.get("entityId");
		SimpleScalar width = (SimpleScalar) params.get("width");
		SimpleScalar height = (SimpleScalar) params.get("height");
		SimpleScalar entity = (SimpleScalar) params.get("entity");
		SimpleScalar fieldName = (SimpleScalar) params.get("fieldName");
		SimpleScalar webUrl = (SimpleScalar) params.get("webUrl");

		StringBuffer html = new StringBuffer();
		html.append("<img");
		if (id != null && !"".equals(id.getAsString()) && entity != null && !"".equals(entity.getAsString())
				&& fieldName != null && !"".equals(fieldName.getAsString()) && webUrl != null
				&& !"".equals(webUrl.getAsString())) {
			html.append(" src=\"" + webUrl + "/report/loadImage?className=" + entity + "&amp;entityId=" + id
					+ "&amp;fieldName=" + fieldName + "\"");
		}
		if (width != null && !"".equals(width.getAsString())) {
			html.append(" width=\"" + width + "\"");
		}
		if (height != null && !"".equals(height.getAsString())) {
			html.append(" height=\"" + height + "\"");
		}
		html.append("/>");

		Writer out = env.getOut();

		out.write(html.toString());
		if (body != null) {
			body.render(out);
		}
	}
}
