using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.IO;

namespace HelloWorld
{
    class Program
    {
        static void Main(string[] args)
        {
            using (StreamReader reader = new StreamReader("input.txt"))
            {
                string line;
                while ((line = reader.ReadLine()) != null)
                {
                    Console.WriteLine(String.Format("Hello {0}!", line));
                }
            }
        }
    }
}
