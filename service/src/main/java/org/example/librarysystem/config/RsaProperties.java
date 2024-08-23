package org.example.librarysystem.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "rsa")
public class RsaProperties {
	/*
	 * @Author cms
	 * @Description //TODO $
	 * @Date $ $
	 * @Param $
	 * @return $
	 **/
	private String privateKey;
	private String publicKey;
}
