package com;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Test {
	public static void main(String[] args) {
		BCryptPasswordEncoder bc=new BCryptPasswordEncoder(4);
        System.out.println(bc.encode("1111"));
	}

}
