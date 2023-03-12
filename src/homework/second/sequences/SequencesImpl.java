package homework.second.sequences;

public class SequencesImpl implements Sequences {
    private int sequence;

    @Override
    public void a(int n) {
        System.out.print("A. ");
        int length = n + 1;
        for (int i = 1; i < length; i++) {
            this.sequence = i + i;
            if (i < n) {
                System.out.print(this.sequence + ", ");
            } else {
                System.out.println(this.sequence + "..."); // Вывод для последнего элемента последовательности
            }
        }
    }

    @Override
    public void b(int n) {
        System.out.print("B. ");
        int length = n + 1;
        for (int i = 1; i < length; i++) {
            this.sequence = i + i - 1;
            if (i < n) {
                System.out.print(this.sequence + ", ");
            } else {
                System.out.println(this.sequence + "..."); // Вывод для последнего элемента последовательности
            }
        }
    }

    @Override
    public void c(int n) {
        System.out.print("C. ");
        int length = n + 1;
        for (int i = 1; i < length; i++) {
            this.sequence = i * i;
            if (i < n) {
                System.out.print(this.sequence + ", ");
            } else {
                System.out.println(this.sequence + "..."); // Вывод для последнего элемента последовательности
            }
        }
    }

    @Override
    public void d(int n) {
        System.out.print("D. ");
        int length = n + 1;
        for (int i = 1; i < length; i++) {
            this.sequence = i * i * i;
            if (i < n) {
                System.out.print(this.sequence + ", ");
            } else {
                System.out.println(this.sequence + "..."); // Вывод для последнего элемента последовательности
            }
        }
    }

    @Override
    public void e(int n) {
        System.out.print("E. ");
        int length = n + 1;
        for (int i = 1; i < length; i++) {
            if (i % 2 == 0) {
                this.sequence = -1;
            } else {
                this.sequence = 1;
            }
            if (i < n) {
                System.out.print(this.sequence + ", ");
            } else {
                System.out.println(this.sequence + "..."); // Вывод для последнего элемента последовательности
            }
        }

    }

    @Override
    public void f(int n) {
        System.out.print("F. ");
        int length = n + 1;
        for (int i = 1; i < length; i++) {
            if (i % 2 == 0) {
                this.sequence = -i;
            } else {
                this.sequence = i;
            }
            if (i < n) {
                System.out.print(this.sequence + ", ");
            } else {
                System.out.println(this.sequence + "..."); // Вывод для последнего элемента последовательности
            }
        }

    }

    @Override
    public void g(int n) {
        System.out.print("G. ");
        int length = n + 1;
        for (int i = 1; i < length; i++) {
            if (i % 2 == 0) {
                this.sequence = -(i * i);
            } else {
                this.sequence = i * i;
            }
            if (i < n) {
                System.out.print(this.sequence + ", ");
            } else {
                System.out.println(this.sequence + "..."); // Вывод для последнего элемента последовательности
            }
        }
    }

    @Override
    public void h(int n) {
        System.out.print("H. ");
        int length = n + 1;
        for (int i = 1; i < length; i++) {
            if (i % 2 == 0) {
                this.sequence = 0;
            } else {
                this.sequence = (i / 2) + 1;
            }
            if (i < n) {
                System.out.print(this.sequence + ", ");
            } else {
                System.out.println(this.sequence + "..."); // Вывод для последнего элемента последовательности
            }
        }

    }

    @Override
    public void i(int n) {
        System.out.print("I. ");
        int length = n + 1;
        this.sequence = 1;
        for (int i = 1; i < length; i++) {
            this.sequence = this.sequence * i;
            if (i < n) {
                System.out.print(this.sequence + ", ");
            } else {
                System.out.println(this.sequence + "..."); // Вывод для последнего элемента последовательности
            }
        }
    }

    @Override
    public void j(int n) {
        System.out.print("J. ");
        int length = n + 1;
        int stepBack = 0;
        int secondStepBack = 1;
        for (int i = 1; i < length; i++) {
            this.sequence = stepBack + secondStepBack;
            if (i < n) {
                System.out.print(this.sequence + ", ");
            } else {
                System.out.println(this.sequence + "..."); // Вывод для последнего элемента последовательности
            }
            secondStepBack = stepBack;
            stepBack = this.sequence;
        }
    }
}
