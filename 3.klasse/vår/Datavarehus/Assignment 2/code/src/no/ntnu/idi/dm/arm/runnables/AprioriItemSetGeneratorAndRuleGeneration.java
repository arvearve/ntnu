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

		// get the data set
		List<ItemSet<String>> transactions=null;
		boolean useSmallDataset=true; //use small or large dataset
		try {
			if(useSmallDataset)
				transactions=readFile("smallDataset.txt");
			else
			transactions = readFile("supermarket.arff");
		} catch (IOException e) {
			e.printStackTrace();
		}

		// print transactions ... just in case
		System.out.println(transactions);

		// threshold
		Double minSupport = .4d;
		System.out.println("We set the relative minsup to " + minSupport);

		AbstractApriori<String> apriori;

//		apriori = new BruteForceApriori<String>(transactions);
//		apriori = new FKMinus1F1Apriori<String>(transactions);
		apriori = new FkMinus1FKMinus1<String>(transactions);

		apriori.apriori(minSupport);

		apriori.generateAllRules();
		
		System.out.println("Generated " + apriori.getRules().size() + " rules.");
		System.out.println("Generated " + apriori.getRules());
	}
}
