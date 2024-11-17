package co.edu.uniquindio.marketplace.marketplaceapp.model;

import java.util.ArrayList;
import java.util.List;

public class RedSocial {
    private static RedSocial instancia;
    private List<Vendedor> vendedores;

    private RedSocial() {
        this.vendedores = new ArrayList<>();
    }

    public static RedSocial getInstance() {
        if (instancia == null) {
            instancia = new RedSocial();
        }
        return instancia;
    }

    public List<Vendedor> getVendedores() {
        return vendedores;
    }

    public void agregarVendedor(Vendedor vendedor) {
        if (vendedores.size() < 10) {
            vendedores.add(vendedor);
        }
    }

    public Vendedor buscarVendedorPorProducto(Producto producto) {
        for (Vendedor vendedor : vendedores) {
            if (vendedor.getProductos().contains(producto)) {
                return vendedor;
            }
        }
        return null;
    }
}