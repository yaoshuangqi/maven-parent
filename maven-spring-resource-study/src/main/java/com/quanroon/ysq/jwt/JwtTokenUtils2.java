package com.quanroon.ysq.jwt;

import io.jsonwebtoken.*;

import java.util.Date;
import java.util.function.Function;

/**
 * jwt工具类,简单版的，比较易懂
 *
 * jwt由三部分组成：
 * 1.Header : token类型和采用的加密算法
 * 加密算法是单向函数散列算法，常见的有MD5、SHA、HAMC。
 * MD5(message-digest algorithm 5) （信息-摘要算法）缩写，广泛用于加密和解密技术，常用于文件校验。校验？不管文件多大，经过MD5后都能生成唯一的MD5值
 * SHA (Secure Hash Algorithm，安全散列算法），数字签名等密码学应用中重要的工具，安全性高于MD5
 * HMAC (Hash Message Authentication Code)，散列消息鉴别码，基于密钥的Hash算法的认证协议。用公开函数和密钥产生一个固定长度的值作为认证标识，用这个标识鉴别消息的完整性。常用于接口签名验证
 *
 * 2.载荷（payload）:  存放有效信息,承载下面两种信息,而下面测试正是采用的标准声明
 * 第一种：
 * 公共的声明 ：
 * 公共的声明可以添加任何的信息，一般添加用户的相关信息或其他业务需要的必要信息.但不建议添加敏感信息，因为该部分在客户端可解密.
 * 私有的声明 ：
 * 私有声明是提供者和消费者所共同定义的声明，一般不建议存放敏感信息，因为base64是对称解密的，意味着该部分信息可以归类为明文信息。
 * 第二种：
 * 标准中注册的声明 (建议但不强制使用) ：
 * iss: jwt签发者
 * sub: 面向的用户(jwt所面向的用户)
 * aud: 接收jwt的一方
 * exp: 过期时间戳(jwt的过期时间，这个过期时间必须要大于签发时间)
 * nbf: 定义在什么时间之前，该jwt都是不可用的.
 * iat: jwt的签发时间
 * jti: jwt的唯一身份标识，主要用来作为一次性token,从而回避重放攻击。
 *
 * 3.签名（signature）： 根据私有密钥对上面的header,payload组合加密
 * header (base64后的)
 * payload (base64后的)
 * secret（我们定义的密钥，存放在服务器中）
 * 这个部分需要base64加密后的header和base64加密后的payload使用.连接组成的字符串，然后通过header中声明的加密方式进行加盐secret组合加密，然后就构成了jwt的第三部分。
 *
 *签名的目的：签名实际上是对头部以及载荷内容进行签名。所以，如果有人对头部以及载荷的内容解码之后进行修改，再进行编码的话，那么新的头部和载荷的签名和之前的签名就将是不一样的。而且，如果不知道服务器加密的密钥的话，得出来的签名也一定会是不一样的。
 * 这样就能保证token不会被篡改。
 * @author Mark sunlightcs@gmail.com
 */
public class JwtTokenUtils2 {

    private String secret="f4e2e52034348f86b67cde581c0f9eb5";//加密秘钥
    private long expire=40;//token有效时长，7天，单位秒
    private String header="token";

    /**
     * 生成jwt token
     */
    public String generateToken(long userId) {
        Date nowDate = new Date();
        //过期时间
        Date expireDate = new Date(nowDate.getTime() + expire * 1000);

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")//头部信息
                .setSubject(userId+"")//主题，一般携带用户有效信息
                .setIssuedAt(nowDate)//生成时间
                .setExpiration(expireDate)//有效期，单位s
                .signWith(SignatureAlgorithm.HS512, secret)//签名，与密钥进行组合HS512加密
                .compact();//压缩字符串
    }

    /**
     * 对jwt token进行解密
     * @param token
     * @return
     */
    public Claims getClaimByToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        }catch (MalformedJwtException e){
            System.out.println("validate is token error "+e);
            e.printStackTrace();
            return null;
        }catch (ExpiredJwtException er){
            System.out.println("token 过期"+er);
            return null;
        }
    }

    /**
     * token是否过期
     * @return  true：过期
     */
    public boolean isTokenExpired(Date expiration) {
        return expiration.before(new Date());
    }


    public static void main(String[] args) {
        JwtTokenUtils2 jwt = new JwtTokenUtils2();
        String token = "";
        //token
//        token = jwt.generateToken(132);
//        System.out.println("token："+token);

        //解密token
        token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9." +
                "eyJzdWIiOiIxMzIiLCJpYXQiOjE1ODk2Mzc3MTYsImV4cCI6MTU4OTYzNzc1Nn0." +
                "nCOa97AC-FyQQ8P5qQad-BDFYhRq7n5RAY_nK9OvbwphEi6i0X_76Iim6ODhl7EzpK2Muutkrm3zy-WrRh7zNQ";

        //Claims claimByToken = jwt.getClaimByToken(token);
        Function<String, Claims> claim = jwt::getClaimByToken;
        Claims claimByToken = claim.apply(token);

        System.out.println(claimByToken.getSubject() + "=====" +claimByToken.getId());
    }





    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public long getExpire() {
        return expire;
    }

    public void setExpire(long expire) {
        this.expire = expire;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }
}
