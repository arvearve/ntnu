package no.ntnu.idi.dm.arm.apriori;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class FKMinus1F1Apriori<V> extends BaseApriori<V> {

	public FKMinus1F1Apriori(List<ItemSet<V>> transactions) {
		super(transactions);
	}

	@Override
	public List<ItemSet<V>> aprioriGen(List<ItemSet<V>> frequentCandidatesKMinus1) {
		Collections.sort(frequentCandidatesKMinus1);
		int allGeneratedCandidatesCounter = 0;
		Set<ItemSet<V>> frequentCandidateSet = new HashSet<ItemSet<V>>();

        for(ItemSet<V> prev: frequentCandidatesKMinus1){
            for(ItemSet<V> single: frequent1Itemsets){

                // Don't join if set already contains item.
                if(prev.intersection(single).size() != 0 ){ break; }
                ItemSet<V> candidate = prev.union(single);

                getAndCacheSupportForItemset(candidate);

                // Only add non-duplicates
                if(!frequentCandidateSet.contains(candidate)){ frequentCandidateSet.add(candidate); }


            }
        }

		return new LinkedList<ItemSet<V>>(frequentCandidateSet);
	}
}
