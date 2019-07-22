package Config;

public class AlipayConfig {


    /**
     * 正式环境请求地址
     */
    public static String ALIPAY_URL = "https://openapi.alipay.com/gateway.do";
    //public static String ALIPAY_URL = "https://openapi.alipaydev.com/gateway.do";
    /**
     * 支付宝分配给开发者的应用ID
     *
     */
    public static String APP_ID = "2019012063113262";

    /**
     * 支付接口名称
     */
    public static String PAY_METHOD = "alipay.trade.app.pay";
    /**
     * 提现接口名称
     */
    public static String TRANSFER_METHOD = "alipay.fund.trans.toaccount.transfer";
    /**
     * 仅支持JSON
     */
    public static String FORMAT = "JSON";
    /**
     * 请求使用的编码格式
     */
    public static String CHARSET = "utf-8";
    /**
     * 商户生成签名字符串所使用的签名算法类型
     */
    public static String SIGN_TYPE = "RSA2";
    /**                                        MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA2z+1j0DXD6QGq8VmeYCn
     APSyFu0mr9IHYTra0EhoogULI632EfXoyYTDgYior21XiuyW2QHO5v2FyItelGHW
     u6B943KSQCula0Ekq/ko0yvpdV2r+Sr+5oJrSSQ0RCb8DS6WY5hXX4ddOJSPUWN/
     B8TuFVQ6dEUYAUE4oFbfSibPpW84+a865bO3pWjORSqqshXdW7fYx/npzEsvtS9y
     Tz+IftTb0lXmaasajuUlkG4GfVNjuqldOJSCB5FHC2dggDcNnoNrGTQyLqwT4l0a
     m3taqGgXEUrHSHAw9WzwaKclQGjvHep89USSjnXD9EeyKCTM7WDkz7QlPKlIXl4A
     PQIDAQAB
     -----
     * 支付宝公钥                              MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAmncphCZVA5qcs+o5LdQujZqgiqZ7aUGTRyZaAMi9QH4Lnl0ybLFOG8Peis8QPJmfEHJpawSH+Hhdure+Z6qhe6SzdMLqgKVDwKdXpBgSaqIIsDr72GTNIkANSTzqozbCJkjB8OatGMGhdp3xLnhBPY3A/Ezwz8c5/lIOUI5bhteDtVih0TVFk3G60gsIGx8ePzZWHsettt9NR6rqvQBNFGnsMvRuloNd4gIjTOR/EXmze0iOIp95UdI4Mjf3qcy3r0rh+tw89aeJdjbMjIKzuu+06RqvuSjBK1jyTejPOT/JxwHYY5XvszWcaLTaTN+JYkB4xGICyhF7JLilZe0jAwIDAQAB
     */                                    // MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAiaFTeVF+F53BunmhozB7MvxNwXDSLz4hxsQ9hirLOhLR7AnpzxqO7GwMGDHx4l2lS4161G2f6rOUZxIG35+o3hUCvvFwZTzGAeVKog/FTke4DHovub1bspw0aRj6GwtEa+Td1weRFIfIo+SnZgy5N+OQk1cSmaIhCmLA04ECCNaD5ipoNecV5ICZWffunHeHAbfRCLUcuq6pLm8eNGS4FG4aMSKa6M87QgrKsJJWbJtKTpZLA/bFvEX3FU+feDser0lsrkI93KPs/FtDvvjLYl2+mDzseD07MrtFUEcxTClNYE+KCFXx963UKOPz5wS1XCkOKRuAeQZwJWdZocwUIQIDAQAB
    public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAiaFTeVF+F53BunmhozB7MvxNwXDSLz4hxsQ9hirLOhLR7AnpzxqO7GwMGDHx4l2lS4161G2f6rOUZxIG35+o3hUCvvFwZTzGAeVKog/FTke4DHovub1bspw0aRj6GwtEa+Td1weRFIfIo+SnZgy5N+OQk1cSmaIhCmLA04ECCNaD5ipoNecV5ICZWffunHeHAbfRCLUcuq6pLm8eNGS4FG4aMSKa6M87QgrKsJJWbJtKTpZLA/bFvEX3FU+feDser0lsrkI93KPs/FtDvvjLYl2+mDzseD07MrtFUEcxTClNYE+KCFXx963UKOPz5wS1XCkOKRuAeQZwJWdZocwUIQIDAQAB";
    /**
     * 商户私钥                        MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCadymEJlUDmpyz6jkt1C6NmqCKpntpQZNHJloAyL1AfgueXTJssU4bw96KzxA8mZ8QcmlrBIf4eF26t75nqqF7pLN0wuqApUPAp1ekGBJqogiwOvvYZM0iQA1JPOqjNsImSMHw5q0YwaF2nfEueEE9jcD8TPDPxzn+Ug5QjluG14O1WKHRNUWTcbrSCwgbHx4/NlYex622301Hquq9AE0Uaewy9G6Wg13iAiNM5H8RebN7SI4in3lR0jgyN/epzLevSuH63Dz1p4l2NsyMgrO677TpGq+5KMErWPJN6M85P8nHAdhjle+zNZxotNpM34liQHjEYgLKEXskuKVl7SMDAgMBAAECggEBAIkB/S8cGPgTYb8rmAAQXiJ37sJMnNZltc68M8TSSRs1MfUL0TgbpJ54A6K3uz0lPbOG5/5mjHxVtz1w0VB+RrG3Cv4cDYiCDjCJvfDs8RjoFDDMgKoV7uXcTmyRvcV7QoZWrr+QvCRjS2Y8/bosLCH+5Bhl3otNuSDdg5jsTD6y81wKpXrlj8OzHOUUwdXidG/5N49kWCR1p5Rc1lSat8rvOHHuKaNwV9u5BaQhezXMPaBNXicEm3e7zEY9vf1ClX6r8RhClsrFpiwtQUAH9f8kvdPLf3OWfG/GXRp3nJEFtyACRu5ezCOSGQ6QQImRCahZrsAZRf5w/DFeK5qPFdECgYEA3d6hs/LliH8dCJMZfZNBr+Sx96pQk7NAbysPHWowCnsdGeAQdjzfG81333bAd6Ry05cfDC2HWNYv7kYTs6RKrZBKKstEns6Y4bhGwM+tTmDrlFZXuLwoejcigWOFnGKCS+K6QI7H2uKB57QYtxyUtGxrtFA7fvdyTA8jhsStCE8CgYEAsjocbtE361UpWaoecIJwJFSgSrkCIG54LLH84Qm0K5FeWhd2GBqsp7RxUK6bLzbO5PrUQ2lEML2gI72BCagE2++gvM3uqTxThQWuGqKE6yNOrm4Y9Ye71M/b8gavF1sGI2IuftKsuUhR/SfqnEnaw6fSrNK/IMegFFwYtF5sGQ0CgYB80DWfidFejEPFAxuf7StSzBDiBsn1jS1+XHbQUWBdRQ0F9WrNWUkZ1ujMqX100OpX5fftXd96NmQYYwvKT25DXZybnmYoAIk40fXbmfkg0p00ga4vmooa1tFugJI1e6VZE1872hbMKAh0VHhi6o0tQqEEeu49au69ovaVZicBOQKBgCMGNnY0mUee+bxMKSU1DPKmysVEbD2RV/54j9htfsGdFKyhtaxw+YMPe8E+PvLdU3U+MZ55vlPRv/mmEQijwyixg6X21jLdzw/yGM/VJkfLLXgr3K3Lmcbtfiym0ZvGYINtlPOJVTYoFyynvnwuK1aN0WHzHGpFsJivrI8tixK1AoGBAMyiMcBRHM/DcogLCG23bAE4OhaOiOj/aQTZ6S8pbNOvoYryiG3BS3vlZJVOVsbMQz4YfC5Pk/IRrofIu5bLMgyrGM3Wd/v/5zWoUgd1I7DZ4dIHLdnA2ucRWwWcqDgaumI1232ixdH3jpOi+NO9TOKxEXuA20oNwXt9VeK9+6+T
     */
    public static String PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCadymEJlUDmpyz6jkt1C6NmqCKpntpQZNHJloAyL1AfgueXTJssU4bw96KzxA8mZ8QcmlrBIf4eF26t75nqqF7pLN0wuqApUPAp1ekGBJqogiwOvvYZM0iQA1JPOqjNsImSMHw5q0YwaF2nfEueEE9jcD8TPDPxzn+Ug5QjluG14O1WKHRNUWTcbrSCwgbHx4/NlYex622301Hquq9AE0Uaewy9G6Wg13iAiNM5H8RebN7SI4in3lR0jgyN/epzLevSuH63Dz1p4l2NsyMgrO677TpGq+5KMErWPJN6M85P8nHAdhjle+zNZxotNpM34liQHjEYgLKEXskuKVl7SMDAgMBAAECggEBAIkB/S8cGPgTYb8rmAAQXiJ37sJMnNZltc68M8TSSRs1MfUL0TgbpJ54A6K3uz0lPbOG5/5mjHxVtz1w0VB+RrG3Cv4cDYiCDjCJvfDs8RjoFDDMgKoV7uXcTmyRvcV7QoZWrr+QvCRjS2Y8/bosLCH+5Bhl3otNuSDdg5jsTD6y81wKpXrlj8OzHOUUwdXidG/5N49kWCR1p5Rc1lSat8rvOHHuKaNwV9u5BaQhezXMPaBNXicEm3e7zEY9vf1ClX6r8RhClsrFpiwtQUAH9f8kvdPLf3OWfG/GXRp3nJEFtyACRu5ezCOSGQ6QQImRCahZrsAZRf5w/DFeK5qPFdECgYEA3d6hs/LliH8dCJMZfZNBr+Sx96pQk7NAbysPHWowCnsdGeAQdjzfG81333bAd6Ry05cfDC2HWNYv7kYTs6RKrZBKKstEns6Y4bhGwM+tTmDrlFZXuLwoejcigWOFnGKCS+K6QI7H2uKB57QYtxyUtGxrtFA7fvdyTA8jhsStCE8CgYEAsjocbtE361UpWaoecIJwJFSgSrkCIG54LLH84Qm0K5FeWhd2GBqsp7RxUK6bLzbO5PrUQ2lEML2gI72BCagE2++gvM3uqTxThQWuGqKE6yNOrm4Y9Ye71M/b8gavF1sGI2IuftKsuUhR/SfqnEnaw6fSrNK/IMegFFwYtF5sGQ0CgYB80DWfidFejEPFAxuf7StSzBDiBsn1jS1+XHbQUWBdRQ0F9WrNWUkZ1ujMqX100OpX5fftXd96NmQYYwvKT25DXZybnmYoAIk40fXbmfkg0p00ga4vmooa1tFugJI1e6VZE1872hbMKAh0VHhi6o0tQqEEeu49au69ovaVZicBOQKBgCMGNnY0mUee+bxMKSU1DPKmysVEbD2RV/54j9htfsGdFKyhtaxw+YMPe8E+PvLdU3U+MZ55vlPRv/mmEQijwyixg6X21jLdzw/yGM/VJkfLLXgr3K3Lmcbtfiym0ZvGYINtlPOJVTYoFyynvnwuK1aN0WHzHGpFsJivrI8tixK1AoGBAMyiMcBRHM/DcogLCG23bAE4OhaOiOj/aQTZ6S8pbNOvoYryiG3BS3vlZJVOVsbMQz4YfC5Pk/IRrofIu5bLMgyrGM3Wd/v/5zWoUgd1I7DZ4dIHLdnA2ucRWwWcqDgaumI1232ixdH3jpOi+NO9TOKxEXuA20oNwXt9VeK9+6+T";

    /**
     * 回调地址
     */
    public static String CALLBACK_URL = "https://www.artepie.com/artepie/alipaynotify";

}
