package com.cloud.computing.container2.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cloud.computing.container2.model.FailureResponse;
import com.cloud.computing.container2.model.RequestParameters;
import com.cloud.computing.container2.model.SuccessResponse;

@RestController
@RequestMapping
@CrossOrigin(origins = "*")
public class CalculateSum {
    
    protected Logger log = LoggerFactory.getLogger(CalculateSum.class);
    FailureResponse errorResponse;
    SuccessResponse successResponse;
	FailureResponse failureResponse;
	boolean flag=false;
	private static final String STORAGE_DIRECTORY = "/home/Unnati_PV_dir";

    @PostMapping(path = "/sum")
    public ResponseEntity<Object> calculateSum(@RequestBody RequestParameters request) {
		try
		{
			//Main logic of reading the file and sum of the given product
			File directory = new File(STORAGE_DIRECTORY);
			File file = new File(directory, request.getFile());
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			HashMap<String, Integer> resultMap = new HashMap<String, Integer>();
			br.readLine();
			String line="";
			while((line =br.readLine())!=null){

				String[] values = line.split(",");
				if(resultMap.containsKey(values[0])){
					resultMap.put(values[0],resultMap.get(values[0])+Integer.parseInt(values[1].trim()));
				}else {
					resultMap.put(values[0], Integer.parseInt(values[1].trim()));
				}
			}
            br.close();

			if(!resultMap.containsKey(request.getProduct())){
				failureResponse=new FailureResponse();
				failureResponse.setFile(request.getFile());
				failureResponse.setError("Invalid JSON input.");
				return new ResponseEntity<>(failureResponse, HttpStatus.INTERNAL_SERVER_ERROR);
			}
            successResponse = new SuccessResponse();
            successResponse.setFile(request.getFile());
            successResponse.setSum(resultMap.get(request.getProduct()));
            return new ResponseEntity<>(successResponse, HttpStatus.OK);			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			failureResponse=new FailureResponse();
			failureResponse.setFile(request.getFile());
			failureResponse.setError("Input file not in CSV format.");
			return new ResponseEntity<>(failureResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}          

    }

}
