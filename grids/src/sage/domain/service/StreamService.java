package sage.domain.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sage.domain.repository.Edge;
import sage.entity.Tweet;
import sage.transfer.CombineGroup;
import sage.transfer.Item;
import sage.transfer.Stream;
import sage.transfer.TweetCard;

@Service
public class StreamService {
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private TweetReadService tweetReadService;
	@Autowired
	private TagService tagService;
	@Autowired
	private TransferService transferService;

	public Stream istream(long userId) {	
		List<TweetCard> tcs = tweetReadService.istream(userId, Edge.NONE, 0);
		Stream stream = new Stream();
		stream.addAll(higherSort(tcs));
		
		return stream;
	}
	
	public Stream istreamAfter(long userId, long edgeId) {  
        List<TweetCard> tcs = tweetReadService.istream(userId, Edge.AFTER, edgeId);
        Stream stream = new Stream();
        stream.addAll(higherSort(tcs));
        
        return stream;
	}
	
	public Stream istreamBefore(long userId, long edgeId) { 
        List<TweetCard> tcs = tweetReadService.istream(userId, Edge.BEFORE, edgeId);
        Stream stream = new Stream();
        stream.addAll(higherSort(tcs));
        
        return stream;
	}

	private List<Item> higherSort(List<TweetCard> tcs) {
		List<Item> cleanList = new ArrayList<>();
		cleanList.addAll(tcs);
		
		//TODO Pull-near
		return combine(tcs);
	}

	private List<Item> combine(List<TweetCard> tcs) {
		List<CombineGroup> groupSeq = new ArrayList<>();
		for (TweetCard tc : tcs) {
			if (tc.getOrigin() != null) {
				long originId = tc.getOrigin().getId();
				CombineGroup foundGroup = findInSeq(originId, groupSeq);
				if (foundGroup != null) {
					foundGroup.addForward(tc);
				}
				else {
					groupSeq.add(CombineGroup.newByFirst(tc));
				}
			}
			else {
				CombineGroup foundGroup = findInSeq(tc.getId(), groupSeq);
				if (foundGroup != null) {
					foundGroup.addOrigin(tc);
				}
				else {
					groupSeq.add(CombineGroup.newByOrigin(tc));
				}
			}
		}

		List<Item> sequence = new ArrayList<>(groupSeq.size());
		for (CombineGroup group : groupSeq) {
			TweetCard singleMember = group.singleMember();
			if (singleMember != null) {
				sequence.add(singleMember);
			}
			else sequence.add(group);
		}
		return sequence;
	}
	
	private CombineGroup findInSeq(long id, List<CombineGroup> groupSequence) {
		for (CombineGroup group : groupSequence) {
			if (group.getOrigin().getId() == id) {
				return group;
			}
		}
		return null;
	}

	public Stream tagStream(long tagId) {
		List<Tweet> tweets = tweetReadService.tweetsByTags(tagService.getQueryTags(tagId));
		Stream stream = new Stream();
		for (Tweet tweet : tweets) {
			stream.add(transferService.getTweetCard(tweet));
		}
		return stream;
	}

	public Stream personalStream(long userId) {
		List<Tweet> tweets = tweetReadService.tweetsByAuthor(userId);
		Stream stream = new Stream();
		for (Tweet tweet : tweets) {
			stream.add(transferService.getTweetCard(tweet));
		}
		return stream;
	}
}
