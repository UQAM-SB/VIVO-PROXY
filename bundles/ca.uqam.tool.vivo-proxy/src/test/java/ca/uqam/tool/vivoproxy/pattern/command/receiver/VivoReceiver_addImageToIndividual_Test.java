package ca.uqam.tool.vivoproxy.pattern.command.receiver;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.iterable.S3Objects;
import com.amazonaws.services.s3.model.S3ObjectSummary;

import ca.uqam.tool.util.credential.VIVO_PROXY_Properties;
import ca.uqam.tool.vivoproxy.pattern.command.AbstractReceiver;
import ca.uqam.tool.vivoproxy.pattern.command.CommandResult;
import ca.uqam.tool.vivoproxy.swagger.api.ApiException;
import ca.uqam.vocab.proxy.model.Image;

/**
 * @author Michel Héon
 *
 */
public class VivoReceiver_addImageToIndividual_Test extends AbstractReceiver {
	public static void main (String[] argv) throws Exception
	{
		AmazonS3 s3 = AmazonS3ClientBuilder.standard().build();
		VivoReceiver vr = new VivoReceiver();
		vr.login(VIVO_PROXY_Properties.getUserName(), VIVO_PROXY_Properties.getPasswd());

		S3Objects.inBucket(s3, "photos-uqam").forEach((S3ObjectSummary objectSummary) -> {
			String imgName = objectSummary.getKey();
			String id = imgName.replace(".jpg", "");
			String bucket = objectSummary.getBucketName();
			System.out.println(objectSummary.toString());
			try {
				Image image = new Image();
				image.setIndividualIRI("http://purl.org/vivo.uqam.ca/data/people#"+id);
				image.setImageURL("https://photos-uqam.s3.ca-central-1.amazonaws.com/"+imgName);
				image.setOrigX(0);
				image.setOrigY(0);
				image.setHeight(270);
				image.setWidth(270);
				CommandResult resu = vr.addImageToIndividual(image);
			} catch (Exception e) {
				// TODO Bloc catch auto-généré
				e.printStackTrace();
			}
		});
	}
}
