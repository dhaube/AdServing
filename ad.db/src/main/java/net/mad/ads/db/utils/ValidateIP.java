/**
 * Mad-Advertisement
 * Copyright (C) 2011 Thorsten Marx <thmarx@gmx.net>
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package net.mad.ads.db.utils;

import java.util.StringTokenizer;
import java.util.regex.Pattern;

public class ValidateIP {
	public static void main(String[] args) {
		System.out.println(validate("127.0.0.1"));
		System.out.println(validate("74.125.45.100"));
		
		
		String inet_aton = INET_ATON("74.125.45.100");
		System.out.println(inet_aton + " " + inet_aton.equals("1249717604"));
		
		
		System.out.println(ip2long("74.125.45.100"));
	}

	private static boolean validate(String iPaddress) {
		final Pattern IP_PATTERN = Pattern
				.compile("^([1-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])(\\."
						+ "([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])){3}$");
		return IP_PATTERN.matcher(iPaddress).matches();
	}
	/**
	 * A.B.C.D
	 * 
	 * ip = ((A*256+B)*256+C)*256 + D
	 * 
	 * @param ip
	 * @return
	 */
	private static String INET_ATON (String ip) {
		if (!validate(ip)) {
			return null;
		}
		String result = null;
		int [] ipns = new int[4]; 
		StringTokenizer tokens = new StringTokenizer(ip, ".");
		int i = 0;
		while (tokens.hasMoreTokens()) {
			String token = tokens.nextToken();
			ipns[i] = Integer.valueOf(token);
			i++;
		}
		
		result = String.valueOf( (((ipns[0] * 256 + ipns[1]) * 256 + ipns[2]) * 256 + ipns[3]) );
		
		return result;
	}
	public static long ip2long(String ipAddress) {
		long f1, f2, f3, f4;
		String tokens[] = ipAddress.split("\\.");
		if (tokens.length != 4) return -1;
		try {
			f1 = Long.parseLong(tokens[0]) << 24;
			f2 = Long.parseLong(tokens[1]) << 16;
			f3 = Long.parseLong(tokens[2]) << 8;
			f4 = Long.parseLong(tokens[3]);
			return f1+f2+f3+f4;
		} catch (Exception e) {
			return -1;
		}
	}
	
	private String long2ip(long n) {
		return ((n >> 24)&0xff)+"."+((n >> 16)&0xff)+ "."+((n >> 8)&0xff)+"."+(n&0xff);
	}
}
