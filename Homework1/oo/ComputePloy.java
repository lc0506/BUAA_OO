package oo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.HashSet;

public class ComputePloy
{	
	Ploy[] Ploylist;
	int Ploynum;
	char[] oplist;
	int opnum;
	public ComputePloy(){
		Ploy[] pl=new Ploy[23];
		this.Ploylist=pl;
		this.Ploynum=0;
		char[] ol=new char[23];
		this.oplist=ol;
		this.opnum=0;
	}
	
	public void getstring(){
		//��ʼ�����û����룬������readin��//
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		String readin=null;
		System.out.println("#���������ݣ�");
		try{
			readin=br.readLine();
		}catch(IOException e){
			e.printStackTrace();
		}
		readin=readin.replaceAll(" ","");
		
		//����������ʽ�ж��Ƿ�Ϸ�//
		String pan="(\\+|-)?\\{\\((\\+|-)?[0-9]{1,6},((\\+?[0-9]{1,6})|(-0{1,6}))\\)(,\\((\\+|-)?[0-9]{1,6},((\\+?[0-9]{1,6})|(-0{1,6}))\\)){0,49}\\}((\\+|-)\\{\\((\\+|-)?[0-9]{1,6},((\\+?[0-9]{1,6})|(-0{1,6}))\\)(,\\((\\+|-)?[0-9]{1,6},((\\+?[0-9]{1,6})|(-0{1,6}))\\)){0,49}\\}){0,19}";
		Pattern patt=Pattern.compile(pan);
		Matcher match=patt.matcher(readin);
		boolean boo=match.matches();


		//�����Ϸ������ERROR//
		if(boo==false){
			System.out.println("ERROR");
		}
		//���Ϸ�����������//
		else{
			//������ȡ�������//
			String ti1="(\\+|-)\\{";
			Pattern p1=Pattern.compile(ti1);
			Matcher mat1=p1.matcher(readin);
			while(mat1.find()){
				this.oplist[this.opnum]=mat1.group(0).charAt(0);
				this.opnum=this.opnum+1;
			}
			//������ȡ��������//
			String[] readlist=readin.split("\\}\\+|\\}-");
			String ti2="(\\+|-)?[0-9]{1,6}";
			Pattern p2=Pattern.compile(ti2);
			int nnn;
			int flag=0;
			for(int i=0;i<readlist.length;i++){
				//��������������//
				int[] numarray=new int[105];
				int narnum=0;
				Matcher mat2=p2.matcher(readlist[i]);
				while (mat2.find()){
					String cnum=mat2.group(0);
					if(cnum.charAt(0)=='+'){
						cnum=cnum.replaceAll("\\+","");
						nnn=Integer.parseInt(cnum);
					}
					else if(cnum.charAt(0)=='-'){
						cnum=cnum.replaceAll("-","");
						nnn=-Integer.parseInt(cnum);
					}
					else {
						nnn=Integer.parseInt(cnum);
					}
					numarray[narnum]=nnn;
					narnum=narnum+1;
				}
				//�ж��Ƿ����ظ�ָ��//
				HashSet<Integer> hashset=new HashSet<Integer>();
				for(int j=1;j<narnum;j=j+2){
					hashset.add(numarray[j]);
				}
				if(hashset.size()!=narnum/2) {
					flag=1;
					break;
				}
				
				Ploy demo=new Ploy(numarray,narnum);
				this.Ploylist[this.Ploynum]=demo;
				this.Ploynum=this.Ploynum+1;
			}
			if(flag==0) {
				this.computeshow();
			}
			else {
				System.out.println("ERROR");
			}
		}
	}
	
	public void computeshow(){
		int[] inilist={0,0};
		int ininum=2;
		Ploy inip=new Ploy(inilist,ininum);

		if(this.Ploynum==this.opnum){
			for(int i=0;i<this.opnum;i++){
				if(this.oplist[i]=='+'){
					inip.add(this.Ploylist[i]);
				}
				else if(this.oplist[i]=='-'){
					inip.sub(this.Ploylist[i]);
				}
			}
		}
		else{
			inip.add(this.Ploylist[0]);
			for(int i=0;i<this.opnum;i++){
				if(this.oplist[i]=='+'){
					inip.add(this.Ploylist[i+1]);
				}
				else if(this.oplist[i]=='-'){
					inip.sub(this.Ploylist[i+1]);
				}
			}
		}
		inip.show();
	}

	public static void main(String[] args) 
	{
		ComputePloy last=new ComputePloy();
		last.getstring();
	}
	
}

