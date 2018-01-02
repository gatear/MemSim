public enum MemoryStrategies implements Strategy {

    FIRST_FIT () {

        @Override
        public void apply (Process P[],int H[],int nproc,int nholes,int flag[]) {

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
                        System.out.println("FIRST FIT: PROCESS- "+(i+1)+" text ALLOCATED IN HOLE-"+ j);
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
                        System.out.println("FIRST FIT: PROCESS- "+(i+1)+" data ALLOCATED IN HOLE-"+ j);
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
                        System.out.println("FIRST FIT: PROCESS- "+(i+1)+" heap ALLOCATED IN HOLE-"+ j);
                        break;
                    }
                    else{
                        j++;
                    }
                }
                if(j==nholes){
                    while(i<nproc)
                    {
                        System.out.println("Process"+(i+1)+" cannot be allocated");
                        i++;
                    }
                    break;
                }
            }

            System.out.println("The Resulting Total External Fragmentation is : "+frag);
        }
        },

    BEST_FIT () {

        @Override
        public void apply (Process P[],int H[],int nproc,int nholes,int flag[]) {
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
                    System.out.println("BEST FIT: PROCESS- "+(i+1)+"text ALLOCATED IN HOLE-"+(loc));
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
                    System.out.println("BEST FIT: PROCESS- "+(i+1)+"data ALLOCATED IN HOLE-"+(loc));
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
                    System.out.println("BEST FIT: PROCESS- "+(i+1)+" heap ALLOCATED IN HOLE-"+(loc));
                    k=0;
                }
                min=1000;
                loc=0;
                k=1;
                if(count==nholes)
                {
                    System.out.println("PROCESS: "+(i+1)+"CANNOT BE ALLOCATED DUE TO INSUFFICCIENT MEMORY");
                }

            }
            System.out.println("The Resulting Total External Fragmentation is : "+frag);
        }
        },

    WORST_FIT () {

        @Override
        public void apply (Process P[],int H[],int nproc,int nholes,int flag[]) {

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
                    k=0;
                }
                max=0;
                loc=0;
                k=1;
                if(count==nholes)
                {
                    System.out.println("PROCESS: "+(i+1)+"CANNOT BE ALLOCATED DUE TO INSUFFICCIENT MEMORY");
                }
            }

            System.out.println("The Resulting Total External Fragmentation is : "+frag);

        }
        },

    PAGING ();

    public void alloc(Process P[],int Pframe ,int nproc,int page_flag[]) {

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
                            System.out.println("PAGING: PROCESS  "+(i+1)+":PAGE: "+(j+1)+" IS ALLOCATED IN FRAME ===> "+ran);

                        }
                    }
                }
                f+=P[i].frag;
            }
            System.out.println("Total Internal Fragmentation is = "+f);
        };   /*to be used only with PAGING*/

}






