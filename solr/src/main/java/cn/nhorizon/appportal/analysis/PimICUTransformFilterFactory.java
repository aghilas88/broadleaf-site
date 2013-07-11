package cn.nhorizon.appportal.analysis;
/**
 * 
 */


import java.util.Map;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.util.TokenFilterFactory;

import com.ibm.icu.text.Transliterator;

/**
 * @author qijb
 *
 */
public class PimICUTransformFilterFactory extends TokenFilterFactory {
	static Transliterator quanpin = Transliterator.getInstance("Han-Latin;NFD;[[:NonspacingMark:][:Space:]] Remove");		
	static Transliterator jianpin = Transliterator.createFromRules(
		    "Han-Latin;",
		    ":: Han-Latin;[[:any:]-[[:space:][\uFFFF]]] { [[:any:]-[:white_space:]] >;::Null;[[:NonspacingMark:][:Space:]]>;",
		    Transliterator.FORWARD);
	static Transliterator jianpinFirstQuanpin = Transliterator.createFromRules(null, 
			":: Han-Latin/Names;[[:space:]][bpmfdtnlgkhjqxzcsryw] { [[:any:]-[:white_space:]] >;::NFD;[[:NonspacingMark:][:Space:]]>;",
			Transliterator.FORWARD);
	@Override
	public void init(Map<String,String> args) {
		super.init(args);
		assureMatchVersion();
	}

	@Override
	public TokenStream create(TokenStream input) {
		 return new PimICUTransformFilter(input, new Transliterator[]{quanpin});
	}

}
