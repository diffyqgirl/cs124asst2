import java.util.Arrays;
import java.io.*;

public class Asst2
{
    
    static int n0 = 512; // default initialization, optional 0 flag alters this. 
    public static void main(String[] args) throws IOException
    {
        if(Integer.parseInt(args[0])>=1)
            n0 = Integer.parseInt(args[0]);
        int n = Integer.parseInt(args[1]);
        File infile = new File(args[2]);
        FileReader fr = new FileReader(infile);
        BufferedReader br = new BufferedReader(fr);
        // input matrices
        int[][] a = new int[n][n];
        int[][] b = new int[n][n];
        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < n; j++)
            {
                a[i][j] = Integer.parseInt(br.readLine());
            }
        }
        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < n; j++)
            {
                b[i][j] = Integer.parseInt(br.readLine());
            }
        }
        br.close();
        fr.close();
        // matrix to output
        int[][] c = new int[n][n];
        c = multiply(a,b);
        /*
        Using a stringbuilder and a single call to println speeds 
        up the program's execution tremendously because file io is 
        slow. 
        */
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < n; i++)
        {
            output.append(c[i][i]).append("\n");
        }
        System.out.println(output.toString());
        
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
        int n = ((a.length + 1)/2) * 2; // readability
        int n2 = n/2;
        /*
        Declaring these 3 temp variables allows us to reduce space, 
        the number of memory allocations the program has to perform, 
        and the number of times the program has to copy (n/2)^2 matrix 
        entries. I do think it's a little less readable, however.
        */
        int[][] a_temp = new int[n2][n2];
        int[][] b_temp = new int[n2][n2];
        int[][] m_temp = new int[n2][n2];

        // to be returned
        int[][] c = new int[a.length][a.length];

        //M1 = (A11 + A22)*(B11 + B22)
        for (int i = 0; i < n2; i++)
        {
            for (int j = 0; j < n2; j++)
            {
                a_temp[i][j] = elt(a, i, j) + elt(a, i + n2, j + n2);
                b_temp[i][j] = elt(b, i, j) + elt(b, i + n2, j + n2);
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
        for (int i = 0; i < n2; i++)
        {
            for (int j = 0; j < n2; j++)
            {
                c[i][j] += m_temp[i][j]; // C11
                if (i + n2 < c.length && j + n2 < c.length)
                    c[i + n2][j + n2] += m_temp[i][j]; // C22
            }
        }

        // M2 = (A21 + A22)(B11)
        for (int i = 0; i < n2; i++)
        {
            for (int j = 0; j < n2; j++)
            {
                a_temp[i][j] = elt(a,i + n2,j) + elt(a,i + n2,j + n2);
                b_temp[i][j] = b[i][j];
            }
        }
        m_temp = multiply(a_temp, b_temp);

        // M2 should be added to C21 and subtracted from C22
        for (int i = 0; i < n2; i++)
        {
            for (int j = 0; j < n2; j++)
            {
                if (i + n2 < c.length)
                    c[i + n2][j] += m_temp[i][j];
                if (i + n2 < c.length && j + n2 < c.length)
                    c[i + n2][j + n2] -= m_temp[i][j];
            }
        }

        // M3 = A11 * (B12-B22)
        for (int i = 0; i < n2; i++)
        {
            for (int j = 0; j < n2; j++)
            {
                a_temp[i][j] = a[i][j];
                b_temp[i][j] = elt(b,i,j + n2) - elt(b,i + n2,j + n2);
            }
        }
        m_temp = multiply(a_temp, b_temp);

        // M3 should be added to c12 and c22
        for (int i = 0; i < n2; i++)
        {
            for (int j = 0; j < n2; j++)
            {
                if (j + n2 < c.length)
                    c[i][j + n2] += m_temp[i][j];
                if (i + n2 < c.length && j + n2 < c.length)
                    c[i + n2][j + n2] += m_temp[i][j];
            }
        }

        // M4 = A22*(B21-B11)
        for (int i = 0; i < n2; i++)
        {
            for (int j = 0; j < n2; j++)
            {
                a_temp[i][j] = elt(a, i + n2, j + n2);
                b_temp[i][j] = elt(b, i + n2, j) - b[i][j];
            }
        }
        m_temp = multiply(a_temp, b_temp);

        // M4 should be added to c11 and c21
        for (int i = 0; i < n2; i++)
        {
            for (int j = 0; j < n2; j++)
            {
                c[i][j] += m_temp[i][j];
                if (i + n2 < c.length)
                    c[i + n2][j] += m_temp[i][j];
            }
        }

        // M5 = (A11 + A12)*B22
        for (int i = 0; i < n2; i++)
        {
            for (int j = 0; j < n2; j++)
            {
                a_temp[i][j] = a[i][j] + elt(a, i, j + n2);
                b_temp[i][j] = elt(b, i + n2, j + n2);
            }
        }
        m_temp = multiply(a_temp, b_temp);

        // M5 should be added to c12 and subtracted from c11
        for (int i = 0; i < n2; i++)
        {
            for (int j = 0; j < n2; j++)
            {
                c[i][j] -= m_temp[i][j];
                if (j + n2 < c.length)
                    c[i][j + n2] += m_temp[i][j];
            }
        }

        // M6 = (A21-A11)*(B11+B12)
        for (int i = 0; i < n2; i++)
        {
            for (int j = 0; j < n2; j++)
            {
                a_temp[i][j] = elt(a, i + n2, j) - a[i][j];
                b_temp[i][j] = b[i][j] + elt(b, i, j + n2);
            }
        }
        m_temp = multiply(a_temp, b_temp);

        // M6 should be added to c22
        for (int i = 0; i < n2; i++)
        {
            for (int j = 0; j < n2; j++)
            {
                if (i + n2 < c.length && j + n2 < c.length)
                    c[i + n2][j + n2] += m_temp[i][j];
            }
        }

        // M7 = (A12-A22)*(B21+B22)
        for (int i = 0; i < n2; i++)
        {
            for (int j = 0; j < n2; j++)
            {
                a_temp[i][j] = elt(a, i, j + n2) - elt(a, i + n2, j + n2);
                b_temp[i][j] = elt(b, i + n2, j) + elt(b, i + n2, j + n2);
            }
        }
        m_temp = multiply(a_temp, b_temp);

        // M7 should be added to C11
        for (int i = 0; i < n2; i++)
        {
            for (int j = 0; j < n2; j++)
            {
                c[i][j] += m_temp[i][j];
            }
        }
        return c;

    }

    public static int elt(int[][] a, int i, int j)
    {
        if (i < a.length && j < a.length)
            return a[i][j];
        return 0;
    }

    public static int[][] multiply(int[][] a, int[][] b)
    {
        int n = a.length;
        if(n >= n0)
            return strassen(a, b);     
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
}
