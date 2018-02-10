package testPackage;

import java.sql.SQLException;
import java.util.*;

import com.squareup.moshi.JsonDataException;

import net.dean.jraw.RedditClient;
import net.dean.jraw.http.NetworkAdapter;
import net.dean.jraw.http.OkHttpNetworkAdapter;
import net.dean.jraw.http.UserAgent;
import net.dean.jraw.models.Listing;
import net.dean.jraw.models.PublicContribution;
import net.dean.jraw.models.SearchSort;
import net.dean.jraw.models.Submission;
import net.dean.jraw.models.SubredditSort;
import net.dean.jraw.models.TimePeriod;
import net.dean.jraw.oauth.Credentials;
import net.dean.jraw.oauth.OAuthHelper;
import net.dean.jraw.pagination.DefaultPaginator;
import net.dean.jraw.pagination.Paginator;
import net.dean.jraw.pagination.SearchPaginator;
import net.dean.jraw.pagination.SearchPaginator.Builder;
import net.dean.jraw.references.SubredditReference;
import net.dean.jraw.tree.CommentNode;
import net.dean.jraw.tree.RootCommentNode;


public class Main {

	public static void main(String[] args) throws SQLException{
		 //Getting input from User
        System.out.print("Please enter keyword: ");
        Scanner input = new Scanner(System.in);
        String keyWord = input.nextLine();
        input.close();
        BehindScene crawl = new BehindScene(keyWord);
        crawl.StartCrawl();
	
	}
	

}
