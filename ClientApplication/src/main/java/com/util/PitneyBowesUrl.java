package com.util;

import org.springframework.beans.factory.annotation.Value;

public class PitneyBowesUrl {
    @Value("${ngp.url}")
    public static String pbUrl = "https://ngp-qa.pitneybowes.com";
    @Value("${ngp.token}")
    public static String pbauthrizationToken = "U3lrMmFBaEZBYmI2R1J2bmRXaUpsZlg0andVN1NHWmk6Mk5zUFdTM1hKVXc5SUJ5MA";

}
