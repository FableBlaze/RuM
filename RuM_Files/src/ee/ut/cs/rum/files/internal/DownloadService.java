package ee.ut.cs.rum.files.internal;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.service.ServiceHandler;

import ee.ut.cs.rum.database.domain.UserFile;

public class DownloadService implements ServiceHandler {
	
	private String downloadServiceId;
	private UserFile userFile;
	
	public DownloadService(UserFile userFile) {
		this.userFile=userFile;
		this.downloadServiceId=String.valueOf(System.currentTimeMillis()) + "_" + userFile.getId();
	}
	
	public String getURL() {
        return RWT.getServiceManager().getServiceHandlerUrl(downloadServiceId);
    }
	
	public void register() {
        try {
            RWT.getServiceManager().registerServiceHandler(downloadServiceId, this);
        } catch (Exception e) {
            Activator.getLogger().info("Failed to register download service handler: " + downloadServiceId, e);
        }
    }
	
	private void unregister() {
        try {
            RWT.getServiceManager().unregisterServiceHandler(downloadServiceId);
        } catch (Exception e) {
        	Activator.getLogger().info("Failed to unregister download service handler: " + downloadServiceId, e);
        }
    }

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		try {
            File file = new File(userFile.getFileLocation());
            
            String mimeType = URLConnection.guessContentTypeFromName(userFile.getOriginalFilename());
            String contentDisposition = String.format("attachment; filename=" + userFile.getOriginalFilename());
            int fileSize = Long.valueOf(file.length()).intValue();

            response.setContentType(mimeType);
            response.setHeader("Content-Disposition", contentDisposition);
            response.setContentLength(fileSize);
            
            OutputStream out = response.getOutputStream();
            Path path = file.toPath();
            Files.copy(path, out);
            out.flush();
        } catch (Exception e) {
            Activator.getLogger().info("Error dispatching download of userFile: " + userFile.getId());
        } finally {
            unregister();
        }
	}

}
