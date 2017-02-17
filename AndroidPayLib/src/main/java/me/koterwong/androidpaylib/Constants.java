/*
 * PayConstants     2016/12/2 17:11
 * Copyright (c) 2016 Koterwong All right reserved
 */
package me.koterwong.androidpaylib;

/**
 * Created by Koterwong on 2016/12/2 17:11
 */
public interface Constants {
  interface AliPay {
    /** 支付宝AppID */
    String APPID = "2017011905223597";
    /** 商户私钥，pkcs8格式 */
    String RSA_PRIVATE = "MIIEpgIBAAKCAQEA5Hv3xf+AJSTAJK3yuxclSg6HJ0ZlNXiHOMauSsgqY5L" +
        "/iadFHisTvl+oxQGUguEOum7Aza5gda5Olt0fx/3iXoiKlQ1fCAzFfOQG8ibmKUxUfq8ZOp2krZb" +
        "PrPUs3rQxjSUGWMy4POTP6IJ9ifYlOQ+Ze93j7hgWdJv5LjXJ4Vqg5sJ6gJgmqw7AQ6ffFo7rLjH" +
        "T/e/Q0haCy/77RB3EycRazQENYkqBKj4QFnSOOzcVmgArTocDJ+kqQEb0pVItB0SHBQ8qygxOAVD" +
        "uW8tab4Jr35Y49MsFonKSzreXddj5Yyr8NBkn3IRn6FxSE68XtrIFcLTCyI5U4KCtClpqyQIDAQA" +
        "BAoIBAQCvKSKAVimlUBFFjV0idNIPfie6wkrgvS/dJOOFTQLCJ3Vj0Co6wId4UNmPfnCeDW8/GZ0" +
        "RkXc9gI2bNpYgudAK7N5g+kk6YKAd6KoyhgO48V1taUsMFsKimZVIYQUvDjFWpWXCOd+cP0XsV+1" +
        "qEJvkrMLbpPYSF8FPUCutc2r7KQ4f52E+x3uwpIyjpnom6qfuRp4nro8q+biKBho4LwxgRHITF0j" +
        "mBlmWhfrBO8Fkx6O/8k14JWnMYDoPOV0Se4Azqei1J/bxOEpq5fICpHpBIXCbbPudodE79NyTSpY" +
        "zRYeYOjTz5K3TveEhrWa29iIz5t1SFLpuhQRThMrWmDYhAoGBAP4LTLFhlEN1wjEdeLBKm/ISzLQ" +
        "3PSY8Btyav0We0qAgR2Pqv1tG0zxBL4I6kY97LJ+D0QrpVvhL3d3PznNsooUGyiul8JW2DsfrjPT" +
        "OuGAyfw6dEy/muFyhWdZJPGdcwpJETx9hPUp/7BCN5gXEYE1fStLyVCpKNbWXJb8ys9x9AoGBAOY" +
        "+SrQrFmGXo0uyMDaLcCFyeLf2U1QHaFIcQ1N1AO6NlPYxdjqNxLMt4wsq4dm+vctLAyWE1d2K7Ry" +
        "Q+183RuSCF7WS8iFOU18zl3+BKKNn02NPBJWKXxiVSfmdHh54BnXOec1ISbOEQjm91CRcSU/j2xR" +
        "TDndl5sxY9EHojTU9AoGBAK+iJkKYIMXG2xvTsUXIPypL/wQ1EGynoWLRBZ3fYOTdjB2zBNrZC18" +
        "j/fF+BnZ5BO+/VT3C9qKw6NHaQnQgTffEKu+kSMFoXa2v1wLFDElNf/Qdek9qplk8H/z7Iuye/9H" +
        "Zsg4Szyn7aLxFLhscmivDztoqOTAHoDxMJAecySaNAoGBAMahuNsETRO2uVcFi9iSpRMF6aVzaeI" +
        "VXedwoJiwWTR0iq12cHQFeEvIa5Zn0V6D0sM+nKFSnxq4zuSVI3e6fXuHzCKGekNHil+YZ7vFVqT" +
        "aXqDPO7ozHOVdbetdo+1PSOt7TNNP6N1pXaTo4srXnxvADHHVUaaqD9n9aAQwNxpVAoGBAMhSRwH" +
        "xwVG8omIEdIbPrn4RBov31yCajzYvoyjtKrgrXwedDD1VmH1pha2rn2rZpxPCG4b/QnSmL61oQQ7" +
        "JaRBGudT2sxHOzQv0/g40y0AqGqsDNj8uXtzg3imU5AT99X/Njox3tVIG41N6D3XyZtkRXe6WZZE" +
        "1AfH9PfFos4oq";
    /** 支付宝回调后台的NotifyUrl */
//    String NOTIFY_URL = "http://sanding.jzbwlkj.com/Api/Notify/index";
    String NOTIFY_URL = "http://1w491841q0.51mypc.cn/electric/index.php?s=/api/Notify/index";
    /** 支付宝公钥 */
    String RSA_PUBLIC = "";
  }

  interface WxPay {
    /** 微信 AppID，在微信开放平台创建应用，并开通支付能力 */
    String APP_ID = "wxd3c051e2c1463b40";
    /** 商户号 */
    String WX_SHOP_NUM = "1397387202";
    /** 微信应用密钥 */
    String WX_API_KEUSTORE = "825cb27cdd2762314dbd4c77d7794241";
  }
}
