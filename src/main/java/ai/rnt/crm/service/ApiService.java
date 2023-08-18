package ai.rnt.crm.service;

import lombok.Data;

@Data
public class ApiService {
 private String name;
 public static void main(String[] args) {
	ApiService ap=new ApiService();
	ap.getName();
}
}
