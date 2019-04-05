package application.domain;

import java.time.LocalDate;
import java.util.Objects;

public class Purchase {

    private int sum;
    private LocalDate date;

    public Purchase(int sum, LocalDate date) {
        this.sum = sum;
        this.date = date;

    }

    public int getSum() {
        return sum;
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Purchase other = (Purchase) obj;
        if (this.sum != other.sum) {
            return false;
        }
        if (!Objects.equals(this.date, other.date)) {
            return false;
        }
        return true;
    }

}
