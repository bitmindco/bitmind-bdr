package com.xmunch.bitmind.bdr.api;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.UUID;

import javax.websocket.server.PathParam;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blockcypher.context.BlockCypherContext;
import com.blockcypher.exception.BlockCypherException;
import com.blockcypher.model.address.Address;
import com.blockcypher.model.nulldata.NullData;
import com.blockcypher.model.transaction.summary.TransactionSummary;
import com.xmunch.bitmind.bdr.BitmindBdrUtils;
import com.xmunch.bitmind.bdr.BitmindBlockchainUtils;


@RestController
class MicroserviceJava extends BitmindBlockchainUtils {
	
	private static final Logger logger = Logger.getLogger(MicroserviceJava.class);
	private static final String BLOCKCHAIN_TRANSACTION_URL = "https://blockchain.info/tx/";

	//TODO: Working in
	@RequestMapping("/address/{name}")
	public Address createAddress(@PathParam("name") String name) throws BlockCypherException {
		
        Address address = blockCypherContext.getAddressService().createAddress();
        
        logger.info(MessageFormat.format("Address {0} created for the organization "+name, address.getAddress()));
        logger.info("Your wallet address is "+ address.getAddress());
        logger.info("\n\nPublic key: "+address.getPublic());
        logger.info("\n\nPrivate key: "+address.getPrivate());
		
        return address;
        
    }
	
	//TODO: Working in
	@RequestMapping("/address/{name}")
	public String  addInfoToTestnet(@PathParam("name") String name) throws BlockCypherException, UnsupportedEncodingException, NoSuchAlgorithmException {
    	BlockCypherContext context = new BlockCypherContext("v1", "btc", "test3", "YOURTOKEN");
    	String uuid = UUID.randomUUID().toString();
    	String organizationObject = "algo" + uuid;
    	byte[] bytesOfMessage = organizationObject.getBytes("UTF-8");

    	String digest1 = BitmindBdrUtils.createDigest(bytesOfMessage);
    	String digest2 = BitmindBdrUtils.createDigest(bytesOfMessage);
    	System.out.println("Obenetmos este digest: "+digest1 + " y "+ digest2);
    	
    	NullData sentNullData = context.getTransactionService().sendNullData(new NullData(digest1, "string"));
    	System.out.println("Transaction hash of data UUID "+uuid+" embed:   " +  sentNullData.getHash());
    	System.out.println("Audit: https://www.blocktrail.com/tBTC/tx/" +sentNullData.getHash() );
    	
    	//Y ahora otros metadatos
    	 sentNullData = context.getTransactionService().sendNullData(new NullData("Bitmind.co Transactions Library Test1 bl", "string"));
    	System.out.println("Transaction hash of data UUID "+uuid+" embed:   " +  sentNullData.getHash());
    	System.out.println("Audit: https://www.blocktrail.com/tBTC/tx/" +sentNullData.getHash() );
    	
    	return sentNullData.getHash();
    }
	
	//TODO: Working in
	@RequestMapping("/check/{transactionAddress}")
		public String  checkTransactions(@PathParam("transacionAddress") String add)  throws BlockCypherException{
    	  Address address = blockCypherContext.getAddressService().getAddress("1JLg3M9H2xS4qbZEkRkvY8eeSDAVW1kho8"); //add
          
          logger.info(MessageFormat.format("\nAddress {0} has the following transactions: ", address.getAddress()));
          Collection<TransactionSummary> transactions = address.getTxrefs();
          
          if(transactions != null && transactions.size() != 0){
        	  for (TransactionSummary transaction : transactions) {
        		 logger.info("\nHash:"+transaction.getTxHash());
        		 logger.info("\nWay:"+ (transaction.isSpent() ? "Output" : "Input") );
        		 logger.info("\nInspect:"+BLOCKCHAIN_TRANSACTION_URL+ transaction.getTxHash());
			}	  
          } 
          
          //TODO: Working in
          return "...";
    }

}