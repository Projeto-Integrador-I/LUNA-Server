package com.luna.app.rs;

import java.util.HashMap;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luna.core.data.Report;
import com.luna.db.providers.ReportManager;
import com.luna.misc.exception.RequestException;

@RestController
public class ReportResource 
    extends
        DefaultResource        
{

    private final ReportManager reportManager = ReportManager.getInstance();

    @GetMapping( value="reports/users" )
    public ResponseEntity<String> generateUserReport() 
    {
        try{
            Report reportAllUsers = new Report() {
                @Override
                public HashMap getParameters() {
                    return new HashMap();
                }

                @Override
                public String getPath() {
                    return "reports/allUsers.jrxml";
                }
            };


            reportManager.createReport( reportAllUsers );

            return null;
        }
        catch ( Exception e ) 
        {   
            return internalServerError( e );
        }
    }

}
