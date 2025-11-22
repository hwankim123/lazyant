package com.hwan.lazyant.openapi.provider.koreainvestment.market;


import lombok.Getter;

@Getter
public class GetMarketOrderDetailsResponse {
    private String rt_cd; //성공 실패 여부
    private String msg_cd; //응답 코드
    private String msg1; //응답 메세지
    private MarketOrderDetails output; //응답 상세

    @Getter
    public static class MarketOrderDetails {
        private String rsym; //실시간조회종목코드
        private String pvol; //전일거래량
        private String open; //시가
        private String high; //고가
        private String low; //저가
        private String last; //현재가
        private String base; //전일종가
        private String tomv; //시가총액
        private String pamt; //전일거래대금
        private String uplp; //상한가
        private String dnlp; //하한가
        private String h52p; //52주최고가
        private String h52d; //52주최고일자
        private String l52p; //52주최저가
        private String l52d; //52주최저일자
        private String perx; //PER
        private String pbrx; //PBR
        private String epsx; //EPS
        private String bpsx; //BPS
        private String shar; //상장주수
        private String mcap; //자본금
        private String curr; //통화
        private String zdiv; //소수점 자리수
        private String vnit; //매매단위
        private String t_xprc; //원환산당일가격
        private String t_xdif; //원환산당일대비
        private String t_xrat; //원환산당일등락
        private String p_xprc; //원환산전일가격
        private String p_xdif; //원환산전일대비
        private String p_xrat; //원환산전일등락
        private String t_rate; //당일환율
        private String p_rate; //전일환율
        private String t_xsgn; //원환산당일기호
        private String p_xsng; //원환산전일기호
        private String e_ordyn; //거래가능여부
        private String e_hogau; //호가단위
        private String e_icod; //업종(섹터)
        private String e_parp; //액면가
        private String tvol; //거래량
        private String tamt; //거래대금
        private String etyp_nm; //ETP 분류명
    }
}
