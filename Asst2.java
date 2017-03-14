import java.util.Arrays;
public class Asst2
{
    static final int MIN = 0;
    static final int MAX = 2;
    public static void main(String[] args)
    {
        int n = Integer.parseInt(args[1]);
        randtests(n);
    }

    public static void randtests(int n)
    {
        int[][] a = new int[n][n];
        int[][] b = new int[n][n];
        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < n; j++)
            {
                a[i][j]= (int)(Math.random()*(MAX-MIN) + MIN);
                b[i][j]= (int)(Math.random()*(MAX-MIN) + MIN);
            }
        }
        int[][] c = multiply(a,b);
        //print2D(c);
    }
    public static int[][] add(int[][] a, int[][] b)
    {
        int[][] c = new int[a.length][a.length];
        for (int i = 0; i < a.length; i++)
        {
            for (int j = 0; j < a.length; j++)
            {
                c[i][j] = a[i][j] + b[i][j];
            }
        }
        return c;
    }

    /*
    //multiplies the submatrices of a and b from index a1 to a2 and b1 to b2 respectively
    public static int[][] idx_multiply(int[][] a, int a1, int a2, int[][] b, int b1, int b2)
    {

    }
    */
    public static int[][] strassen(int[][] a, int[][] b)
    {
        int n = a.length; // readability
        /*
        Declaring these 3 temp variables allows us to reduce space, 
        the number of memory allocations the program has to perform, 
        and the number of times the program has to copy (n/2)^2 matrix 
        entries. I do think it's a little less readable, however.
        */
        int[][] a_temp = new int[n/2][n/2];
        int[][] b_temp = new int[n/2][n/2];
        int[][] m_temp = new int[n/2][n/2];

        // to be returned
        int[][] c = new int[n][n];

        //M1 = (A11 + A22)*(B11 + B22)
        for (int i = 0; i < n/2; i++)
        {
            for (int j = 0; j < n/2; j++)
            {
                a_temp[i][j] = a[i][j] + a[i + n/2][j + n/2];
                b_temp[i][j] = b[i][j] + b[i + n/2][j + n/2];
            }
        }
        m_temp = multiply(a_temp, b_temp); 
        /*
            m_temp now contains M1. we now add M1 onto the correct 
            subelements of c, which is what will be returned. We 
            could separately declare C11, C12, C21, C22, and then 
            combine them into C, but that would require more copying 
            and memory allocation, so we don't. 
        */
        //M1 should be added to C11 and C22
        for (int i = 0; i < n/2; i++)
        {
            for (int j = 0; j < n/2; j++)
            {
                c[i][j] += m_temp[i][j];//C11
                c[i + n/2][j + n/2] += m_temp[i][j];//C22
            }
        }

        //

    }
    public static int[][] multiply(int[][] a, int[][] b)
    {
        int n = a.length;
        int[][] c = new int[n][n];
        for (int i = 0; i < n; i++)
        {
            for (int k = 0; k < n; k++)
            { 
                int A = a[i][k];
                for (int j = 0; j < n; j++)
                {
                    c[i][j] += A*b[k][j];
                }
            }
        }
        return c;
    }

    // for testing
    public static void print2D(int[][] a)
    {
        for (int i = 0; i < a.length; i++)
        {
            System.out.println(Arrays.toString(a[i]));
        }
    }
}
