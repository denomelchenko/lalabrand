package com.lalabrand.ecommerce.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.translate.TranslateClient;
import software.amazon.awssdk.services.translate.model.TranslateTextRequest;
import software.amazon.awssdk.services.translate.model.TranslateTextResponse;

@Component
public class TranslationService {
    @Value("${aws.translation.access.key}")
    private String accessKey;
    @Value("${aws.translation.secret.key}")
    private String secretKey;

    public String textTranslate(String sourceLanguage, String targetLanguage, String text) {
        TranslateTextRequest textRequest = TranslateTextRequest.builder().sourceLanguageCode(sourceLanguage).targetLanguageCode(targetLanguage).text(text).build();
        TranslateClient translateClient = TranslateClient.builder()
                .region(Region.US_WEST_2)
                .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey)))
                .build();
        TranslateTextResponse textResponse = translateClient.translateText(textRequest);
        return textResponse.translatedText();
    }
}
