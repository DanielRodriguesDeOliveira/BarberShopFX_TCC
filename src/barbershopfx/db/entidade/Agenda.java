package barbershopfx.db.entidade;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 *
 * @author daniel
 */

public class Agenda 
{
    protected int id;
    protected LocalDate data;
    protected LocalTime horario;
    protected Funcionario funcionario;
    protected Cliente cliente;

    public Agenda() 
    {
    }

    public Agenda(LocalDate data, LocalTime horario, Funcionario funcionario, Cliente cliente) {
        this.data = data;
        this.horario = horario;
        this.funcionario = funcionario;
        this.cliente = cliente;
    }

    
    public Agenda(int id, LocalDate data, LocalTime horario, Funcionario funcionario, Cliente cliente) {
        this.id = id;
        this.data = data;
        this.horario = horario;
        this.funcionario = funcionario;
        this.cliente = cliente;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalTime getHorario() {
        return horario;
    }

    public void setHoraraio(LocalTime horario) {
        this.horario = horario;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente_id(Cliente cliente) {
        this.cliente = cliente;
    }
}
