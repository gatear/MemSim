

import ascii.table.Table;

import java.util.Arrays;
import java.util.Scanner;

public class Memory
{
    public static int number_holes , memory_size =256,total,low,high,no_proc, page_frame, hole_condition = 0, size = 20;

    private static String [][] processTableData, holesTableData;

    private static String [] processTableHeader = {"PID ", "PROCESS SIZE [BYTES]", "TEXT [BYTES]", "DATA [BYTES]", "HEAP [BYTES]"};

    private static String [] holesTableHeader = {"FRAGMENT ID", "SIZE [BYTES]"};

    private static int first_flag[]=new int[size], best_flag[]=new int[size],  worst_flag[]= new int[size],  page_flag[] = new int[size], hole[]= new int[size];

    static { page_frame = memory_size /32; }
    /* NUMBER OF PAGES USING PAGES OF 32 BYTES */

    public static void main(String ...args)
    {
        int k=1;
        System.out.println("ENTER NUMBER OF HOLES/ FRAGMENTS: ");
        Scanner in = new Scanner(System.in);
        number_holes = in.nextInt();                     //ENTERING NUMBER OF HOLES

        do {
        if(number_holes <= 0)
        {
            System.out.println("ERROR!!!REENTER NUMBER OF HOLES: ");
            number_holes = in.nextInt();

        }else if (number_holes > hole.length) {
            System.out.println("ERROR!!!REENTER A SMALLER THAN "+ hole.length +" NUMBER OF HOLES: ");
            number_holes = in.nextInt();
        }else { hole_condition =1; } } while (hole_condition == 0);

        total= memory_size;
        low=(total/ number_holes)/2;
        high=(total/ number_holes)*2;

        System.out.println("SMALLEST POSSIBLE FRAGMENT [BYTES] = "+low);
        System.out.println("LARGEST POSSIBLE FRAGMENT [BYTES] = "+high+"\n");

        while(k==1)
        {
            for (int i = 0; i<(number_holes -1); i++)
            {
                double db = Math.random()*(high-low)+low;  //RANDOMLY GENERATING HOLE SIZES-----low is  inclusive---high is exclusive
                hole[i]=(int) db;
                total -= hole[i];
            }
            hole[number_holes -1]=total;
            if(total>=low&&total<=high)
                k=0;
            else
                total= memory_size;
        }

        holesTableData = new String [number_holes][2];

        for(int i = 0; i< number_holes; i++)
        {
            holesTableData[i][0] = String.valueOf(i);
            holesTableData[i][1] = String.valueOf(hole[i]);
        }

        String holesTable = Table.getTable(holesTableHeader, holesTableData);
        System.out.println(holesTable);

        int maxsize = Arrays.stream(hole).max().getAsInt();
        int minsize = Arrays.stream(hole).min().getAsInt();

        System.out.println("LARGEST EFFECTIVE FRAGMENT [BYTES] = "+maxsize+ "\n");


        System.out.println("ENTER NUMBER OF PROCESSES");  // ENTER NUMBER OF PROCESSES
        Scanner inc = new Scanner(System.in);
        no_proc = inc.nextInt();
        Process[] P= new Process[no_proc];


        for(int i=0;i<no_proc;i++)
        {
            P[i]=new Process(minsize,maxsize);
        }

        /* CONVERTS THE ARRAY TO PRINT THE ASCII TABLE */
        processTableData = Arrays.asList(P)
                                 .stream()
                                 .map(p -> p.toStringArray() )
                                 .toArray(String[][]::new);

        String procTable = Table.getTable(processTableHeader, processTableData);

        System.out.println(procTable);

        int ch;			//ASKING FOR THE KIND OF ALLOCATION
        do
        {
            System.out.println("****************************************************************************");
            System.out.println("*********************SELECT THE MEMORY METHOD*******************************");
            System.out.println("****************************************************************************");
            System.out.print("\n\t1.SEGMENTATION\n\t2.PAGING\n\t3.EXIT\n");
            Scanner chk= new Scanner(System.in);
            ch=chk.nextInt();
            switch (ch) {
                case 1:
                    int choice;
                    do {
                        System.out.println("SELECT THE ALLOCATION METHOD");
                        System.out.print("\n1.FIRST FIT\n2.BEST FIT\n3.WORST FIT\n4.EXIT\n");
                        Scanner cho= new Scanner(System.in);
                        choice=cho.nextInt();

                        /*Strategy pattern using Enum*/
                        switch (choice) {
                            case 1: //Arrays of Processes,Holes--,FirstFit
                                MemoryStrategies.FIRST_FIT.apply(P, hole, no_proc, number_holes,first_flag);
                                break;
                            case 2:
                                MemoryStrategies.BEST_FIT.apply(P, hole, no_proc, number_holes, best_flag);
                                break;
                            case 3:
                                MemoryStrategies.WORST_FIT.apply(P, hole, no_proc, number_holes, worst_flag);
                                break;
                            case 4:
                                System.out.println("\nYOU HAVE EXITED THE PROGRAM\n");
                                break;
                            default:
                                System.out.println("INVALID CHOICE\nPLEASE CHOOSE AGAIN\n");
                                break;
                        }

                    } while (choice!=4);

                    break;
                case 2:
                    MemoryStrategies.PAGING.alloc(P, page_frame,no_proc,page_flag);
                    break;

                case 3:
                    System.out.println("\nYOU HAVE EXITED THE PROGRAM\n");
                    break;
                default:
                    System.out.println("INVALID CHOICE\nPLEASE CHOOSE AGAIN\n");
                    break;
            }
        }while(ch!=3);
    }


}