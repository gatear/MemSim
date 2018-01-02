import ascii.table.Table;

public enum MemoryStrategies implements Strategy {

    FIRST_FIT () {

        @Override
        public void apply (Process P[],int H[],int nproc,int nholes,int flag[]) {

            String [] head = {"PID","PROCESS SEGMENT","FRAGMENT ID"," FRAGMENT SIZE [BYTES]","DIFF [BYTES]"};
            String [][] data = new String[nproc*3][5];

            int i,j=0,frag=0;

            int count=0;

            for(i=0;i<nproc;i++)
            {   //ALLOCATING TEXT SECTIONS.

                while(j<nholes)
                {
                    if(P[i].text<=H[j] && flag[j]==0)
                    {
                        frag+=H[j]-P[i].text;
                        flag[j]=1;
                        count++;

                        data[i][0] = P[i].getProc_id();
                        data[i][1] = "TEXT";
                        data[i][2] = String.valueOf(j);
                        data[i][3] = String.valueOf(H[j]);
                        data[i][4] = String.valueOf(H[j] - P[i].text);

                        break;
                    }
                    else
                    {
                        j++;               // going to the next hole ~ fragment
                    }
                }
                j=0;
                while(j<nholes)
                { 		//ALLOCATING DATA SECTION
                    if(P[i].data<=H[j] && flag[j]==0)
                    {
                        frag+=H[j]-P[i].data;
                        flag[j]=1;
                        count++;

                        data[i+nproc][0] = P[i].getProc_id();
                        data[i+nproc][1] = "DATA";
                        data[i+nproc][2] = String.valueOf(j);
                        data[i+nproc][3] = String.valueOf(H[j]);
                        data[i+nproc][4] = String.valueOf(H[j] - P[i].data);

                        break;
                    }
                    else
                    {
                        j++;
                    }
                }
                j=0;
                while(j<nholes)
                {	//ALLOCATING HEAP SECTION
                    if(P[i].heap<=H[j] && flag[j]==0)
                    {
                        frag+=H[j]-P[i].heap;
                        flag[j]=1;
                        count++;

                        data[i+2*nproc][0] = P[i].getProc_id();
                        data[i+2*nproc][1] = "HEAP";
                        data[i+2*nproc][2] = String.valueOf(j);
                        data[i+2*nproc][3] = String.valueOf(H[j]);
                        data[i+2*nproc][4] = String.valueOf(H[j] - P[i].heap);
                        break;
                    }
                    else{
                        j++;
                    }
                }
                if(j==nholes){
                    while(i<nproc)
                    {
                        System.out.println("PROCESS "+(i+1)+" CANNOT BE ALLOCATED !!");
                        i++;
                    }
                    return;
                }
            }

            System.out.println(Table.getTable(head,data));
            System.out.println("THE RESULTING TOTAL FRAGMENTATION IS: "+frag);
        }
        },

    BEST_FIT () {

        @Override
        public void apply (Process P[],int H[],int nproc,int nholes,int flag[]) {
            String [] head = {"PID","PROCESS SEGMENT","FRAGMENT ID"," FRAGMENT SIZE [BYTES]","DIFF [BYTES]"};
            String [][] data = new String[nproc*3][5];

            int min=1000,loc=0,j,i,frag=0,temp, k=1,count=0;

            for(i=0;i<nproc;i++)
            {					//FINDING THE BEST FIT FOR THE TEXT SECTION OF PROCESS
                for( j=0;j<nholes;j++)
                {
                    temp=H[j];
                    if(flag[j]==0 && P[i].text<=temp)
                    {
                        if(min>=temp)
                        {
                            min=temp;
                            loc=j;
                        }
                    }
                    // k=1;
                }
                if(k==1 && count<nholes && flag[loc]==0)
                {

                    flag[loc]=1;
                    count++;
                    frag=frag+(H[loc]-P[i].text);

                    data[i][0] = P[i].getProc_id();
                    data[i][1] = "TEXT";
                    data[i][2] = String.valueOf(loc);
                    data[i][3] = String.valueOf(H[loc]);
                    data[i][4] = String.valueOf(H[loc] - P[i].text);

                    k=0;
                }

                min=1000;
                loc=0;
                k=1;
                //FINDING THE BEST FIT FOR THE DATA SECTION OF PROCESS
                for( j=0;j<nholes;j++)
                {
                    temp=H[j];
                    if(flag[j]==0 && P[i].data<=temp)
                    {
                        if(min>=temp)
                        {
                            min=temp;
                            loc=j;
                        }
                    }
                    // k=1;
                }
                if(k==1&& count<nholes && flag[loc]==0)
                {
                    flag[loc]=1;
                    frag=frag+(H[loc]-P[i].data);
                    count++;

                    data[i+nproc][0] = P[i].getProc_id();
                    data[i+nproc][1] = "DATA";
                    data[i+nproc][2] = String.valueOf(loc);
                    data[i+nproc][3] = String.valueOf(H[loc]);
                    data[i+nproc][4] = String.valueOf(H[loc] - P[i].data);

                    k=0;
                }

                min=1000;
                loc=0;
                k=1;

                //FINDING THE BEST FIT FOR THE HEAP SECTION OF PROCESS

                for( j=0;j<nholes;j++)
                {
                    temp=H[j];
                    if(flag[j]==0 && P[i].heap<=temp)
                    {
                        if(min>=temp)
                        {
                            min=temp;
                            loc=j;
                        }
                    }
                    // k=1;
                }
                if(k==1&& count<nholes && flag[loc]==0)
                {
                    flag[loc]=1;
                    frag=frag+(H[loc]-P[i].heap);
                    count++;

                    data[i+2*nproc][0] = P[i].getProc_id();
                    data[i+2*nproc][1] = "HEAP";
                    data[i+2*nproc][2] = String.valueOf(loc);
                    data[i+2*nproc][3] = String.valueOf(H[loc]);
                    data[i+2*nproc][4] = String.valueOf(H[loc] - P[i].heap);

                    k=0;
                }
                min=1000;
                loc=0;
                k=1;
                if(count==nholes)
                {
                    System.out.println("PROCESS: "+(i+1)+" CANNOT BE ALLOCATED DUE TO INSUFFICCIENT MEMORY");
                    return;
                }

            }
            System.out.println(Table.getTable(head,data));
            System.out.println("THE RESULTING TOTAL FRAGMENTATION IS: "+frag);
        }
        },

    WORST_FIT () {

        @Override
        public void apply (Process P[],int H[],int nproc,int nholes,int flag[]) {

            String [] head = {"PID","PROCESS SEGMENT","FRAGMENT ID"," FRAGMENT SIZE [BYTES]","DIFF [BYTES]"};
            String [][] data = new String[nproc*3][5];
            int max=0,loc=0,j,i,frag=0,temp, k=1,count=0;

            for(i=0;i<nproc;i++)
            {			//FINDING THE WORST FIT FOR THE TEXT SECTION OF PROCESS
                for( j=0;j<nholes;j++)
                {
                    temp=H[j];
                    if(flag[j]==0 && P[i].text<=temp)
                    {
                        if(max<=temp)
                        {
                            max=temp;
                            loc=j;
                        }
                    }
                    // k=1;
                }
                if(k==1 && count<nholes && flag[loc]==0)
                {

                    flag[loc]=1;
                    count++;
                    frag=frag+(H[loc]-P[i].text);

                    data[i][0] = P[i].getProc_id();
                    data[i][1] = "TEXT";
                    data[i][2] = String.valueOf(loc);
                    data[i][3] = String.valueOf(H[loc]);
                    data[i][4] = String.valueOf(H[loc] - P[i].text);

                    System.out.println("WORST FIT: PROCESS- "+(i+1)+" text ALLOCATED IN HOLE-"+(loc));
                    k=0;
                }

                max=0;
                loc=0;
                k=1;
                //FINDING THE WORST FIT FOR THE DATA SECTION OF PROCESS

                for( j=0;j<nholes;j++)
                {
                    temp=H[j];
                    if(flag[j]==0 && P[i].data<=temp)
                    {
                        if(max<=temp)
                        {
                            max=temp;
                            loc=j;
                        }
                    }
                    // k=1;
                }
                if(k==1&& count<nholes && flag[loc]==0)
                {
                    flag[loc]=1;
                    frag=frag+(H[loc]-P[i].data);
                    count++;

                    data[i+nproc][0] = P[i].getProc_id();
                    data[i+nproc][1] = "DATA";
                    data[i+nproc][2] = String.valueOf(loc);
                    data[i+nproc][3] = String.valueOf(H[loc]);
                    data[i+nproc][4] = String.valueOf(H[loc] - P[i].data);

                    System.out.println("WORST FIT: PROCESS- "+(i+1)+" data ALLOCATED IN HOLE-"+(loc));
                    k=0;
                }

                max=0;
                loc=0;
                k=1;

                //FINDING THE WORST FIT FOR THE HEAP SECTION OF PROCESS

                for( j=0;j<nholes;j++)
                {
                    temp=H[j];
                    if(flag[j]==0 && P[i].heap<=temp)
                    {
                        if(max<=temp)
                        {
                            max=temp;
                            loc=j;
                        }
                    }
                    // k=1;
                }
                if(k==1&& count<nholes && flag[loc]==0)
                {
                    flag[loc]=1;
                    frag=frag+(H[loc]-P[i].heap);
                    count++;
                    System.out.println("WORST FIT: PROCESS- "+(i+1)+" heap ALLOCATED IN HOLE-"+(loc));

                    data[i+nproc*2][0] = P[i].getProc_id();
                    data[i+nproc*2][1] = "HEAP";
                    data[i+nproc*2][2] = String.valueOf(loc);
                    data[i+nproc*2][3] = String.valueOf(H[loc]);
                    data[i+nproc*2][4] = String.valueOf(H[loc] - P[i].heap);

                }
                max=0;
                loc=0;
                k=1;
                if(count==nholes)
                {
                    System.out.println("PROCESS: "+(i+1)+" CANNOT BE ALLOCATED DUE TO INSUFFICCIENT MEMORY");
                    return;
                }
            }

            System.out.println(Table.getTable(head,data));
            System.out.println("THE RESULTING TOTAL FRAGMENTATION IS: "+frag);


        }
        },

    PAGING ();

    public void alloc(Process P[],int Pframe ,int nproc,int page_flag[]) {

            String [] tableHeader = { "PID","PID'S PAGE","MEMORY FRAME" };
            String [][] tableData = new String [nproc][3];

            int i,j,count=0,f=0;
            for(i=0;i<nproc;i++)
            {
                for(j=0;j<P[i].pages;j++)
                {
                    int k=1;
                    while(k==1 && count< Pframe)
                    {
                        int ran=(int)(Math.random()*Pframe);
                        if(page_flag[ran]==0)
                        {
                            page_flag[ran]=1;
                            k=0;
                            count++;

                            tableData[i][0] = P[i].getProc_id();
                            tableData[i][1] = String.valueOf(j+1);
                            tableData[i][2] = String.valueOf(ran);
                        }
                    }
                }
                f+=P[i].frag;
            }

            String table = Table.getTable(tableHeader, tableData);
            System.out.println(table+"\n");
            System.out.println("TOTAL INTERNAL FRAGMENTATION [BYTES] = "+f);
        };   /*to be used only with PAGING*/


}






