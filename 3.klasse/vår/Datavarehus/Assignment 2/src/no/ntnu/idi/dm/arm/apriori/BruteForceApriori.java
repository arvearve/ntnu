package no.ntnu.idi.dm.arm.apriori;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BruteForceApriori<V> extends BaseApriori<V> {

    private Map<Integer, List<ItemSet<V>>> candidates = new HashMap<Integer, List<ItemSet<V>>>();

    public BruteForceApriori(List<ItemSet<V>> transactions) {
        super(transactions);
    }

    @Override
    public void apriori(Double minSupport) {
        // Initialize with single items
        List<ItemSet<V>> singles = getAllItemsetsOfSizeOne();
        List<ItemSet<V>> current  = new ArrayList<ItemSet<V>>();
        candidates.put(1, singles);
        boolean done = false;
        int k = 2; // start on item sets with length 2

        // Generate all candidates. Iterate until no new item sets are found. This is brute force, after all.
        while (true){
            // Join each item set in previous level with a single item to generate new item sets
            for(ItemSet<V> prev: candidates.get(k-1)){
                for(ItemSet<V> single:singles){

                    // Don't join if set already contains item.
                    if(prev.intersection(single).size() != 0 ){ break; }
                    ItemSet<V> candidate = prev.union(single);

                    // Don't add duplicates
                    if(!current.contains(candidate)){ current.add(candidate); }
                }
            }

            // Stop processing if we couldn't add more candidate sets.
            if(current.size()==0){break;}

            // Save frequent item sets of current length
            candidates.put(k, new ArrayList(current));

            // Stop looping if we are done.
            current.clear();
            k++;
        }

        // Prune infrequent candidates, add the frequent ones to frequentItemSets
        for(Map.Entry<Integer, List<ItemSet<V>>> entry: candidates.entrySet()){
            Integer key = entry.getKey();
            List<ItemSet<V>> candidateList = entry.getValue();

            // Why doesn't the povided prune method do this on the passed list? ~_~
            for(ItemSet<V> candidate: candidateList){getAndCacheSupportForItemset(candidate);}
            List<ItemSet<V>> frequentz = pruneInfrequentCandidates(minSupport, candidateList);

            // Don't add empty entries to frequentItemSets
            if(frequentz.size()>0){
                frequentItemSets.put(key, frequentz);
            }
        }
        System.out.println(frequentItemSets);
    }
}