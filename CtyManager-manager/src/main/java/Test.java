
public class Test {
	public static void main(String[] args) {
		String[] values = {
				"ee_1=[):]","ee_2=[:D]","ee_3=[)]","ee_4=[:-o]","ee_5=[:p]","ee_6=[(H)]","ee_7=[:@]","ee_8=[:s]","ee_9=[:$]","ee_10=[:(]","ee_11=[:'(]","ee_12=[:|]:","ee_13=[(a)]","ee_14=[8o|]","ee_15=[8-|]","ee_16=[+o(]","ee_17=[<o)]","ee_18=[|-)]","ee_19=[*-)]","ee_20=[:-#]","ee_21=[:-*]","ee_22=[^o)]","ee_23=[8-)]","ee_24=[({)]","ee_25=[(})]","ee_26=[jie]","ee_27=[jk]","ee_28=[ll]","ee_29=[bz]","ee_30=[shui]","ee_31=[fd]","ee_32=[zhm]","ee_33=[yiw]","ee_34=[xu]","ee_35=[yun]","ee_36=[zhem]","ee_37=[shuai]","ee_38=[kl]","ee_39=[qiao]","ee_40=[zj]","ee_41=[ch]","ee_42=[gg]","ee_43=[gz]","ee_44=[qd]","ee_45=[zk]","ee_46=[zhh]","ee_47=[yhh]","ee_48=[tuu]","ee_49=[bs]","ee_50=[wq]","ee_51=[kk]","ee_52=[yx]","ee_53=[tx]","ee_54=[xia]","ee_55=[kel]","ee_56=[cd]","ee_57=[xig]","ee_58=[pj]","ee_59=[lq]","ee_60=[pp]","ee_61=[kf]","ee_62=[fan]","ee_63=[zt]","ee_64=[(F)]","ee_65=[(W)]","ee_66=[(k)]","ee_67=[(|)]","ee_68=[(u)]","ee_69=[dg]","ee_70=[(*)]","ee_71=[zhd]","ee_72=[dao]","ee_73=[zq]","ee_74=[pch]","ee_75=[bb]","ee_76=[(S)]","ee_77=[(#)]","ee_78=[lw]","ee_79=[(R)]","ee_80=[yb]","ee_81=[(D)]","ee_82=[ruo]","ee_83=[ws]","ee_84=[shl]","ee_85=[bq]","ee_86=[gy]","ee_87=[qt]","ee_88=[cj]","ee_89=[aini]","ee_90=[NO]","ee_91=[OK]","ee_92=[aiq]","ee_93=[fw]","ee_94=[tiao]","ee_95=[fad]","ee_96=[oh]","ee_97=[zhq]","ee_98=[kt]","ee_99=[ht]","ee_100=[tsh]","ee_101=[hsh]","ee_102=[jd]","ee_103=[jw]","ee_104=[xw]","ee_105=[ztj]","ee_106=[ytj]"
		};
		StringBuffer strs = new StringBuffer();
		for(String str : values) {
			String[] keys = str.split("=");
			strs.append("\""+keys[1]+"\":\""+keys[0]+"\",");
		}
		System.out.println(""+strs.toString());
	}
}
