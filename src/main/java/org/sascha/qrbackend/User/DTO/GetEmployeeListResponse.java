package org.sascha.qrbackend.User.DTO;


    public class GetEmployeeListResponse {

        private boolean success;
        private String employeeId;
        private String message;
        private String employeeFirstName;
        private String employeeLastName;
        private String employeeRole;

        public GetEmployeeListResponse() {
        }

        public GetEmployeeListResponse(boolean success, String employeeId, String message, String employeeFirstName, String employeeLastName, String employeeRole) {
            this.success = success;
            this.employeeId = employeeId;
            this.message = message;
            this.employeeFirstName = employeeFirstName;
            this.employeeLastName = employeeLastName;
            this.employeeRole = employeeRole;
        }

        public boolean isSuccess() {
            return success;
        }

        public String getEmployeeId() {
            return employeeId;
        }

        public String getMessage() {
            return message;
        }

        public String getEmployeeFirstName() {
            return employeeFirstName;
        }

        public String getEmployeeLastName() {
            return employeeLastName;
        }

        public String getEmployeeRole() {
            return employeeRole;
        }

    }
