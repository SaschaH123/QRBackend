package org.sascha.qrbackend.User.DTO;

public class GetCompanyListResponse {

        private String companyId;
        private String companyName;

        public GetCompanyListResponse() {}

        public GetCompanyListResponse(String companyId, String companyName) {
            this.companyId = companyId;
            this.companyName = companyName;
        }

        public String getCompanyId() {
            return companyId;
        }

        public String getCompanyName() {
            return companyName;
        }
    }
