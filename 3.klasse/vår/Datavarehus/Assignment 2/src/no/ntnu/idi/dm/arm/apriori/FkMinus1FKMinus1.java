package no.ntnu.idi.dm.arm.apriori;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class FkMinus1FKMinus1<V> extends BaseApriori<V> {

	public FkMinus1FKMinus1(List<ItemSet<V>> transactions) {
		super(transactions);
	}

	@Override
	public List<ItemSet<V>> aprioriGen(
		   List<ItemSet<V>> frequentCandidatesKMinus1) {

		Collections.sort(frequentCandidatesKMinus1);
		int allGeneratedCandidatesCounter = 0;
		Set<ItemSet<V>> frequentCandidateSet = new HashSet<ItemSet<V>>();

        for(ItemSet<V> prev: frequentCandidatesKMinus1){
            for(ItemSet<V> single: frequentCandidatesKMinus1){

                // Don't join with self

                ItemSet<V> candidate = prev.union(single);

                // Only keep candidates that are "allowed": They have k-2 common elements.
                if(candidate.size() != prev.size()+1){ continue;}
                getAndCacheSupportForItemset(candidate);

                // Only add non-duplicates
                if(!frequentCandidateSet.contains(candidate)){ frequentCandidateSet.add(candidate); }
            }
        }

		return new LinkedList<ItemSet<V>>(frequentCandidateSet);
	}

}
