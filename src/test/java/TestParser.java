import java.util.HashMap;
import java.util.Map;
import org.bytedeco.javacpp.Loader;
import org.nd4j.linalg.api.buffer.DataBuffer;
import org.nd4j.linalg.api.buffer.FloatBuffer;
import org.nd4j.linalg.api.buffer.util.AllocUtil;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.testng.annotations.Test;
import utils.ImportInitialWeights;
import utils.PrintUtils;
import utils.PrintedWeight;
import utils.PrintedWeights;
import utils.json.JSON;

public class TestParser {

    private final static String json = "{\"fitness\":700.0,\"weights\":[{\"key\":\"0_b\",\"values\":[0.5540108,0.83110994,1.9265523,1.2399149,1.3367767,0.8141825,0.5153367,0.0077352524,0.3942715,1.7066436,2.198173,1.3719349,1.2849618,2.0240464,-1.8804426]},{\"key\":\"1_b\",\"values\":[-0.54665613,1.1335331,1.3854306,3.1260755,0.30061173,-0.1694529,1.2987514,0.13705277,-1.760309,-1.2784235,1.0070395,0.27974504]},{\"key\":\"2_b\",\"values\":[0.7618729,-0.73425806,1.203618,1.4507321,-2.517139,-3.1584673,0.79223925,3.103419,2.7997575,-0.89578605]},{\"key\":\"3_b\",\"values\":[1.0033607,2.4152122,-0.5529676,0.20438093,1.1015186,0.5169117,0.31330466]},{\"key\":\"0_W\",\"values\":[0.78388035,0.3766917,0.38231355,2.010273,-1.903661,0.9499041,1.0324314,1.7078295,1.5034531,2.1559863,0.94231075,-0.33230594,0.6870238,1.1059945,0.050609052,0.73500323,-0.25756863,2.6557276,2.3186226,-0.51627225,-0.18826711,2.0668933,-1.5769234,-0.9237713,-3.1269035,0.4633965,-0.09086388,-0.0438326,0.3787225,1.084002,-1.1603988,-0.3538705,-1.3139154,-0.43220103,0.35112488,-0.6375683,-0.6143567,0.88484406,1.8507638,0.7285307,-0.48597288,0.66840255,-0.32002765,3.1537073,-3.1701224,1.9340336,2.2206724,-0.44460648,0.40676573,0.3710256,1.6290144,-1.4644032,0.28265643,0.39082313,1.4310669,-0.079770625,2.7846768,2.3501759,1.819665,3.9130878,5.220199,1.8974797,0.577097,2.8137856,1.8859317,-0.9908793,-0.5990147,1.0336695,1.9542732,1.0451366,3.1171148,-0.28068456,2.0284653,0.5524387,1.5824158,-3.4192276,2.324822,2.7139068,-0.75996745,0.4240985,1.2520363,2.7556708,1.7859137,1.3204398,0.07608524,1.237025,-1.3988104,2.4690986,-1.8040698,1.047884,-1.0780289,2.7224517,-1.8249359,-0.16910693,1.2565726,0.7804881,-0.27386054,-0.023754478,0.37793997,-1.0490676,-1.3396163,-0.4165078,0.8399896,0.87795246,0.5573025,1.7180122,2.7888591,0.7467944,0.7003825,1.3859873,2.0229697,0.1419071,-1.2822628,1.9918182,-1.131216,1.0226581,0.26629072,2.1592536,2.148371,1.5173469,-1.9620876,0.3694588,3.3841393,1.873688,2.960641,-1.4865412,-0.257892,-0.14035806,1.7375824,0.2721634,-1.0780841,1.0519933,0.57285994,-1.0325446,1.1691861,0.43195668,1.6261961,-0.539202,-0.20329666,-0.6468088,0.45987096,-1.7532902,-1.1869042,1.8773258,0.25230515,0.2799461,0.46642527,0.07375923,-2.9067738,0.5016373,-0.07499546,2.5770626,0.8540276,-0.23377705,0.2791695,-0.21572867,-0.3718279,0.40935847,-0.5322005,-2.023829,-1.257623,0.8517269,0.087204635,-0.7726654,-0.76591396,-0.37476844,-0.037674367,0.53679377,1.3547496,-0.3785552,0.3150367,-0.5667928,-1.2911568,-4.340163,2.5549476,-2.1278613,3.2955244,0.25642648,2.0060499,-0.6075711,0.5433988,2.64033,1.798924,0.67113274,1.4790809,0.68630123,-1.2456565,1.6176744,1.3452566,-0.40857354,0.00883162,0.05850476,0.93172765,-0.4316284,0.010985553,0.30141103,-1.0071787,0.7334247,0.9523085,3.5817304,-0.18345055,-0.25300074,1.0758874,-0.1731232,-0.29959124,-0.65778714,0.5253815,-0.029991813,0.7002106,1.2812221,-2.695872,-3.006616,0.14274758,0.37236112,-3.8300073,1.0178425,2.8743145,-2.3212445,-0.9676245,-1.3214605,1.5162091,-0.26668203,1.956774,-0.19476345,-0.88994426,-0.28183454,3.3191004,1.8118834,2.6518118,-0.6424902,-0.7264321,1.0411687,-0.4822255,0.69687104,2.1979892,2.179443,-0.39097798,0.43662715,-0.64222914,0.6794133,1.6794047,2.5281699,-0.93613666,-0.3988809,0.4585344,-0.88010675,0.08574194,-1.5468073,0.51664823,3.0621068,1.4632418,-0.7064536,1.7611092,-2.1314306,-1.6593878,-1.8340786,-1.497513,3.4926991,-0.021379232,3.0301576,-0.6670814,1.1090295,-0.4421835,0.18953174,4.84304,-0.09954578,-0.32140929,-0.13946629,-0.18630138,-1.8029082,-0.82838136,-1.2500559,0.75126666,0.5708371,1.8628886,-0.63356227,1.9926767,1.9832578,0.2729246,1.402091,-0.37309963,-2.2267604,0.53654957,1.4342821,4.7363815,0.20279537,-0.93013024,-0.7148593,2.907565,-3.199429,-1.5361855,1.5251794,-1.8757248,0.93082315,-0.20126048,1.6981362,1.7265198,2.023804,-0.64019173,0.06445509,0.26790392,-0.8131139,-1.7946556,1.0989244,3.388798,-1.414005,3.7491467,1.2751358,0.9348907,-0.5979583,0.17020151,1.1129878,0.9803719,0.6243051,0.19446683]},{\"key\":\"1_W\",\"values\":[-1.4634978,-2.2803404,-0.95280373,0.16899475,2.6669724,-1.1996267,0.02379676,-0.411507,2.0397594,-0.58218086,-0.475919,-0.80996937,-0.84089917,-3.2229037,0.5960843,1.1692977,1.4967659,1.8746173,-1.7360206,1.4929693,0.33773214,-2.1649394,-1.8126681,1.0056825,1.4178088,-0.55013853,0.89846987,-0.4326036,1.4574252,3.000635,-0.55449945,-0.45360285,1.2045069,3.5773332,-1.4652295,2.0151138,3.3609245,-0.36808592,-0.10128371,0.21853548,-2.2452044,-0.133066,-1.7265506,1.7169604,-3.400582,1.0648005,0.8990122,-1.9154451,-2.0016541,1.6006427,2.1589582,0.7533832,0.8243531,1.1477051,0.8772162,1.7448125,2.0943124,3.2730007,-0.9267936,-0.92006165,-1.5607421,-0.6691097,2.3906238,0.1858849,-0.43892193,-0.4455108,1.3047523,2.3244405,3.0164425,-0.39335752,0.42840028,0.4143306,2.2218027,-0.2155391,-0.8415218,1.0078475,1.0024695,0.10185596,0.19967568,0.6903872,-2.1575246,3.6625237,-1.0242476,1.1411343,0.73457175,2.191497,-1.1058238,1.9787935,1.1822029,-1.2480502,-1.757637,-2.5081427,2.3604758,0.11190808,0.4344949,-0.48808417,-1.5075223,-3.4548504,1.9657377,2.062802,0.33015287,-0.15912497,0.54223996,1.3583097,3.2899287,-1.6244098,1.1336981,1.5514662,-0.12845057,-0.082820654,-2.0565665,-0.12244863,1.2072272,-4.6668854,0.7953485,0.051593006,-0.15658177,-1.389073,1.9420688,-1.8063304,3.8519433,-0.70373756,0.6167676,-0.94096124,1.8298047,-0.1875704,0.8748557,-0.82292074,-1.5839341,-1.4794791,-0.5758613,-0.26616874,-0.14467421,-1.8506603,-0.9386178,1.7715302,-1.058481,0.49818534,0.86171335,1.0747159,1.3424749,1.1899977,1.5320966,-0.57133234,0.06985521,1.3584585,0.06668704,0.08257699,-0.8266137,-1.7291192,1.186229,0.39821428,0.9781435,3.3393908,1.3640888,0.013301611,1.5654504,3.039969,1.4437363,-2.4771926,2.0225413,-0.52233166,4.0758414,3.3864613,-1.4919932,1.6809816,2.680365,1.3292623,-0.37970018,1.3440167,1.1415324,-1.3892086,-0.17138907,2.1370058,2.6033885,0.22049522,2.2017562,0.3786813,1.1711416,-1.0512092]},{\"key\":\"2_W\",\"values\":[0.32449192,1.0996275,-1.2309694,0.050480545,2.272662,0.19454956,0.5473815,-0.03780651,2.4556103,1.5004523,0.661457,-0.023083448,0.8158901,-1.3924859,0.3405813,1.3895147,-1.1913162,0.31328863,0.6681784,0.42353022,-0.83830184,-1.3897266,0.29603952,-1.2571645,0.97742933,1.284761,-0.758859,-0.39972135,1.1174179,2.8105004,0.11969715,-0.6423589,-0.5500424,0.26798207,-0.83862835,2.9111948,-2.425295,-1.3877991,0.5444443,-1.4561832,-2.0664744,1.539357,-0.31576854,3.0498955,0.69117206,1.0696082,0.78795934,0.9888786,1.811991,2.0240743,-3.0955,3.055828,3.733222,1.7962676,-0.3222351,-1.255343,1.4719074,1.7356398,-0.120393485,3.2051034,-2.3883603,-1.3708136,-0.6697795,1.9596992,-0.19439298,0.8831369,1.7983253,-1.4098024,1.837465,1.8754869,0.8109952,0.577199,-2.4968464,2.2605247,0.3568793,-1.8664744,-3.1735609,-0.9616592,-0.7857505,1.2740811,-0.49176437,-1.1866889,2.8633041,0.548873,-0.021758556,0.7570997,-1.3160799,2.1790373,1.8168287,0.06490952,1.1349735,0.5163403,-1.1821339,0.06459558,-2.1109335,1.6339544,-0.4601755,0.21153063,1.603019,2.0353472,-0.033165455,0.14766611,-1.8466828,0.61132497,-0.7890318,1.2877293,-4.491326,-0.33243012,-1.1228834,-1.9112058,-1.44908,-0.5818837,2.7659137,-1.103241,1.8127263,1.6497154,0.55772847,1.1904178,-0.045760356,0.55436045]},{\"key\":\"3_W\",\"values\":[2.6038065,1.6762924,-1.9052422,-1.3304579,1.1295036,1.8443079,3.485247,1.0538985,0.32245028,-0.037489593,-0.51475084,3.4024305,0.710908,-0.16329086,1.2146746,1.471611,1.4353406,-1.1256356,1.4039707,0.776972,0.24176389,0.8279242,-0.46223053,3.288685,2.9484775,1.3040261,-0.98851407,0.9700398,1.1669782,1.0173787,-0.73768836,1.8970761,0.66308045,0.2173633,1.0793736,1.2111392,1.0834773,2.2139385,-1.5794275,-1.9091799,0.4791426,1.0274179,3.4250894,-0.057422757,-2.0430171,2.110137,-0.43230322,-0.7812869,-0.60149306,3.4168277,0.35222808,0.18833351,0.35404393,1.0657196,0.9925863,0.008424163,-1.0299467,-1.5361435,0.9356024,1.0581807,1.8133996,-0.5143323,1.5343847,2.9464092,-0.19492537,-0.012709618,4.2352633,0.94920456,-0.37404114,0.645442]}]}";

    @Test
    public void test() {
        PrintedWeights printedWeights = JSON.fromJson(json, PrintedWeights.class);
        Map<String, INDArray> map = new HashMap<>();

        for (PrintedWeight weight : printedWeights.getWeights()) {
            INDArray array = Nd4j.create(weight.getValues());
            map.put(weight.getKey(), array);
        }

        System.out.println(map);
    }
}
