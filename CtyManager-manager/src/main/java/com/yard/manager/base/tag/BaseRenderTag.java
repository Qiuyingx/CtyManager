package com.yard.manager.base.tag;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

public abstract class BaseRenderTag extends BaseTag {

	@SuppressWarnings("rawtypes")
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
			throws TemplateException, IOException {
		render(env, params, loopVars, body);
	}
	
	@SuppressWarnings("rawtypes")
	public abstract void render (Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body);
	
	protected void renderBody(Environment env, TemplateDirectiveBody body) throws IOException, TemplateException {
        if (body != null) {
            body.render(env.getOut());
        }
    }
	
	protected void renderString(Environment env, String str) throws IOException, TemplateException {
        Writer w = env.getOut();
        w.write(str);
        // writer不能关闭，关闭后，模版之后的内容不能显示出来！
    }
}
