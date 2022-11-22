package test.example.demobot.service;

import com.sun.syndication.feed.synd.SyndCategory;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import test.example.demobot.interfaces.FeedParser;
import test.example.demobot.models.Post;

import java.io.IOException;
import java.net.URL;

/**
 * @author Artem Chernikov
 * @version 1.0
 * @since 21.11.2022
 */
@Component
@Slf4j
public class LentaParser implements FeedParser {
    private static final String LINK = "https://lenta.ru/rss";

    public Post parseLastPost() {
        SyndFeedInput syndFeedInput = new SyndFeedInput();
        Post post = null;
        try {
            SyndFeed syndFeed = syndFeedInput.build(new XmlReader(new URL(LINK)));
            post = createPost((SyndEntry) syndFeed.getEntries().get(0));
        } catch (FeedException | IOException e) {
            log.error("Произошла ошибка " + e.getMessage());
        }
        return post;
    }

    private Post createPost(SyndEntry post) {
        return new Post(post.getAuthor(), post.getTitle(),
                ((SyndCategory) post.getCategories().get(0)).getName(), post.getDescription().getValue(),
                post.getLink(), post.getPublishedDate());
    }

}
