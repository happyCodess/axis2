package utils;
import java.util.Iterator;
import org.apache.axiom.om.OMElement;
import org.apache.axis2.AxisFault;
import org.apache.axis2.context.MessageContext;
public class LoginCheck {
		/**
		 * ��һ�仰���ܼ����� ��������ϸ������
		 * 
		 * @throws AxisFault
		 */
		public static void checkUserPwd() throws AxisFault
		{
			MessageContext msgContext = MessageContext.getCurrentMessageContext();
			// ��ȡHead
			Iterator list = (Iterator) msgContext.getEnvelope().getHeader()
					.getFirstElement().getChildren();
			String Username = "";
			String Password = "";
			while (list.hasNext())
			{
				OMElement element = (OMElement) list.next();
				if (element.getLocalName().equals("Username"))
				{
					Username = element.getText();
				}
				if (element.getLocalName().equals("Password"))
				{
					Password = element.getText();
				}
			}
			if (!Username.equals("toone") || !Password.equals("111111"))
			{
				throw new AxisFault(
						" Authentication Fail! Check username/password ");
			}
		}
	}

