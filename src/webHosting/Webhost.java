package webHosting;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

//import org.apache.log4j.BasicConfigurator;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.CredentialsProvider;

import utility.StringConstants;

import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.BasicConfigurator;


public class Webhost {
	
	private String remoteRepoURL;
	private String defaultCommitMessage;
	private String gitHubUsername;
	private String gitHubPassword;
	private String localPath;
	private Git git;
	
	public Webhost(String url, String myLocalPath, String username, String password) {
		// Avoid log4j warnings
		//BasicConfigurator.configure();
		remoteRepoURL = url;
		defaultCommitMessage = "default commit";
		gitHubUsername = username;
		gitHubPassword = password;
		localPath = myLocalPath;
	}
	
	//Clone the repository from remote URL 
	public void cloneRepo(){		
		//Don't put any slashes
		File localRepo = new File(localPath);
		try {
			//Delete testrepo before cloning, for testing purposes
			// because cannot clone into non empty folder
			FileUtils.deleteDirectory(localRepo);
			//Clone a new repo
			git = Git.cloneRepository()
					  .setURI( remoteRepoURL )
					  .setDirectory(new File(localPath)) //Must be file, not string
					  .call(); //Should close, not required though
		} catch (GitAPIException e) { //creating repo exceptions
			e.printStackTrace();
		}  catch (IOException e) {  // creating file exceptions
			e.printStackTrace();
		}
	}
	// adds changes of user folder inside repo
	private void addChanges(String user, String website) throws IOException, GitAPIException {
		
		//Open git repo because not cloned every time
		git = Git.open( new File( localPath ) );
		
		//stage added files
		//setUpdate ensures removal of deleted files
		//String folder = user+"/"+website;
		String folder = user+"/";
		//regular add for new files, but not removed or modified
		git.add().addFilepattern(folder).call();
		// when setUpdate is true, rm and mod files are added, but new files are not added
		git.add().setUpdate(true).addFilepattern(folder).call();
	}
	
	// commits changes default message
	private void commitChanges() throws GitAPIException {
		commitChanges(defaultCommitMessage);
	}
	// custom commit message
	private void commitChanges(String message) throws GitAPIException {
		git.commit().setMessage(message).call();
	}
	// push changes
	public void pushChanges(String user, String website) {
		pushChanges(user, website, defaultCommitMessage);
	}
	
	public void pushChanges(String user, String website, String message) {
		try {
			addChanges(user, website);
			commitChanges(message);
			//Authenticate
			CredentialsProvider credentialsProvider = 
					new UsernamePasswordCredentialsProvider(gitHubUsername, gitHubPassword );
			//Push to online repo
			git.push().setRemote( remoteRepoURL )
				.setCredentialsProvider( credentialsProvider ).call();
		} catch (GitAPIException e) { 
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	// delete a website from user
	public void deleteWebsite(String user, String website){
		//Delete the directory holding website
		File localWebsite = new File (localPath+"/"+user+"/"+website);
		try {
			FileUtils.deleteDirectory(localWebsite);
		} catch (IOException e) {
			e.printStackTrace();
		}
		//push changes to github
		pushChanges(user, website, "deleted website: "+website);
	}
	
	// delete a website from 
	public void deleteWebpage(String user, String website, String webpage){
			//Delete the directory holding website
			File localWebpage = new File (localPath+"/"+user+"/"+website+"/"+webpage+".html");
			localWebpage.delete();
			//push changes to github
			pushChanges(user, website, "deleted webpage: "+webpage+" from "+"website: "+website);
		}
	
	public void deleteUser(String user) {
		File localUser = new File(localPath+"/"+user);
		try {
			FileUtils.deleteDirectory(localUser);
			git = Git.open( new File( localPath ) );
			git.add().setUpdate(true).addFilepattern(user).call();
			commitChanges("deleted user: "+user);
			CredentialsProvider credentialsProvider = 
					new UsernamePasswordCredentialsProvider(gitHubUsername, gitHubPassword );
			//Push to online repo
			git.push().setRemote( remoteRepoURL )
				.setCredentialsProvider( credentialsProvider ).call();
		} catch (GitAPIException e) { 
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	//Test functions of Webhost
	public static void main(String[] args) {
		String remoteURL = "https://github.com/mypagecs201/mypagecs201.github.io.git";
		String username = "mypagecs201";
		String password = "usctrojan1";
		//Name of folders
		String user = "ali";
		String website = "ibelieve";
		//String localPath = getServletContext().getRealPath(StringConstants.LOCALREPO);
		Webhost myPage = new Webhost (remoteURL, "C:/newRepo", username, password);
		//Clone
//		myPage.cloneRepo();
		
//		// put a test webpage into user/website folder
		File source = new File("WebContent/samplePage.html");
		File dest = new File("C:/newRepo"+"/"+user+"/"+website+"/"+"samplePage.html");
		try {
			FileUtils.copyFile(source, dest);
		} catch (IOException e) {
			e.printStackTrace();
		}
//		//push samplePage to github
//		myPage.pushChanges(user, website, "changed samplePage");
//		//myPage.deleteUser(user);
       myPage.deleteWebpage(user, website, "samplePage");

//		//page stored at https://mypagecs201.github.io/user/website/samplePage.html
	}
}
