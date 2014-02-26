package no.ntnu.idi.dm.arm.runnables;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import no.ntnu.idi.dm.arm.apriori.AbstractApriori;
import no.ntnu.idi.dm.arm.apriori.BruteForceApriori;
import no.ntnu.idi.dm.arm.apriori.FKMinus1F1Apriori;
import no.ntnu.idi.dm.arm.apriori.FkMinus1FKMinus1;
import no.ntnu.idi.dm.arm.apriori.ItemSet;

public class AprioriItemSetGeneratorAndRuleGeneration {


	public static ArrayList<ItemSet<String>> readFile(String filename) throws IOException
	{
		BufferedReader reader=new BufferedReader(new FileReader(new File(filename)));
		ArrayList<String> attributeNames=new ArrayList<String>();
		ArrayList<ItemSet<String>> itemSets=new ArrayList<ItemSet<String>>();
		String line=reader.readLine();
		 line=reader.readLine();

		while(line!=null)
		{
			if (line.contains("#") || line.length()<2)
			{
				line=reader.readLine();
				continue;
			}
			if(line.contains("attribute"))
			{
				int startIndex=line.indexOf("'");
				if(startIndex>0)
				{
					int endIndex=line.indexOf("'", startIndex+1);
					attributeNames.add(line.substring(startIndex+1,endIndex ));
				}
				
			}
			else
			{
				ItemSet<String> is=new ItemSet<String>();				
				StringTokenizer tokenizer=new StringTokenizer(line,",");
				int attributeCounter=0;
				String itemSet="";
				while(tokenizer.hasMoreTokens())
				{
					String token=tokenizer.nextToken().trim();
					if(token.equalsIgnoreCase("t"))
					{
						String attribute=attributeNames.get(attributeCounter);
						
						itemSet+=attribute+",";
						is.addItem(attribute);
					}
					attributeCounter++;
				}
				itemSets.add(is);
			}
			line=reader.readLine();
		}
		reader.close();
		return itemSets;
		
	}

	public static void main(String[] args) {
        List<ItemSet<String>> smallTransactions = null;
        List<ItemSet<String>> largeTransactions = null;

        // get the data set
        try {
            smallTransactions = readFile("smallDataset.txt");
            largeTransactions = readFile("supermarket.arff");
        } catch (IOException e) {
            e.printStackTrace();
        }


		// threshold
		Double minSupport = .4d;
		System.out.println("We set the relative minsup to " + minSupport);

		AbstractApriori<String> brute, f1, km1, bruteBig, f1Big, km1Big;

		brute = new BruteForceApriori<String>(smallTransactions);
		f1 = new FKMinus1F1Apriori<String>(smallTransactions);
		km1 = new FkMinus1FKMinus1<String>(smallTransactions);
        bruteBig = new BruteForceApriori<String>(largeTransactions);
		f1Big = new FKMinus1F1Apriori<String>(largeTransactions);
		km1Big = new FkMinus1FKMinus1<String>(largeTransactions);


        System.out.println("\n\n\n---- Brute Force, small dataset ----");
        brute.apriori(minSupport);
        brute.generateAllRules();
		System.out.println("Generated " + brute.getRules().size() + " rules.");
		System.out.println("Generated " + brute.getRules());


        System.out.println("\n\n\n---- F_{k-1} x F_1, small dataset ----");
        f1.apriori(minSupport);
        f1.generateAllRules();
        System.out.println("Generated " + f1.getRules().size() + " rules.");
        System.out.println("Generated " + f1.getRules());


        System.out.println("\n\n\n---- F_{k-1} x F_{k-1}, small dataset ----");
        km1.apriori(minSupport);
        km1.generateAllRules();
        System.out.println("Generated " + km1.getRules().size() + " rules.");
        System.out.println("Generated " + km1.getRules());


        System.out.println("\n\n\n---- Brute Force, large dataset: Infeasible. Not going to run.----");


        System.out.println("\n\n\n---- F_{k-1} x F_1, large dataset ----");
        f1Big.apriori(minSupport);
        f1Big.generateAllRules();
        System.out.println("Generated " + f1Big.getRules().size() + " rules.");
        System.out.println("Generated " + f1Big.getRules());


        System.out.println("\n\n\n---- F_{k-1} x F_{k-1}, large dataset ----");
        km1Big.apriori(minSupport);
        km1Big.generateAllRules();
        System.out.println("Generated " + km1Big.getRules().size() + " rules.");
        System.out.println("Generated " + km1Big.getRules());
	}
}
