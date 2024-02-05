package com.etrade.puggo.third.aws.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * @author zhenyu
 * @version 1.0
 * @description: PaymentMethodDTO
 * @date 2024/2/5 10:21
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentMethodDTO {

    private BillingDetailsDTO billingDetails;

    private MetadataDTO metadata;

    private Boolean livemode;

    private Integer created;

    private String type;

    private String id;

    private CardDTO card;

    private CustomerDTO customer;

    private String object;

    public static class BillingDetailsDTO {
        private AddressDTO address;
        private String phone;
        private String name;

        public static class AddressDTO {
        }
    }

    public static class MetadataDTO {
    }

    public static class CardDTO {
        private String country;
        private String last4;
        private String funding;
        private ThreeDSecureUsageDTO threeDSecureUsage;
        private Integer expMonth;
        private String description;
        private Integer expYear;
        private String issuer;
        private String iin;
        private ChecksDTO checks;
        private String fingerprint;
        private String brand;

        public static class ThreeDSecureUsageDTO {
            private Boolean supported;
        }

        public static class ChecksDTO {
            private String cvcCheck;
            private String addressLine1Check;
            private String addressPostalCodeCheck;
        }
    }

    public static class CustomerDTO {
        private AddressDTO address;
        private Boolean livemode;
        private String invoicePrefix;
        private String taxExempt;
        private Integer created;
        private Integer nextInvoiceSequence;
        private String description;
        private Boolean deleted;
        private Integer balance;
        private String phone;
        private Boolean delinquent;
        private String name;
        private String currency;
        private String id;
        private String email;

        public static class AddressDTO {
            private String country;
            private String city;
            private String state;
            private String postalCode;
            private String line2;
            private String line1;
        }
    }
}
