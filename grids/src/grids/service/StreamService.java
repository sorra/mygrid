package grids.service;

import grids.entity.Tweet;
import grids.transfer.CombineGroup;
import grids.transfer.Item;
import grids.transfer.Stream;
import grids.transfer.TweetCard;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StreamService {
	
	@Autowired
	private TweetReadService tweetReadService;
	@Autowired
	private TagService tagService;

	public Stream istream(long userId) {	
		List<TweetCard> tcs = tweetReadService.istream(userId);
		
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
					foundGroup.getForwards().add(tc);
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
		List<Tweet> tweets = tweetReadService.tweetsByTags(tagService.descendants(tagId));
		
		Stream stream = new Stream();
		for (Tweet tweet : tweets) {
			stream.add(tweetReadService.getTweetCard(tweet));
		}
		return stream;
	}

	public Stream personalStream(long userId) {
		return null;
	}
}
