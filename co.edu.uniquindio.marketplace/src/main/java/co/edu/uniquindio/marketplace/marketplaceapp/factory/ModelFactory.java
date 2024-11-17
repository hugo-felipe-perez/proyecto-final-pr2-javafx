package co.edu.uniquindio.marketplace.marketplaceapp.factory;
import co.edu.uniquindio.marketplace.marketplaceapp.model.RedSocial;
import co.edu.uniquindio.marketplace.marketplaceapp.model.Vendedor;
import co.edu.uniquindio.marketplace.marketplaceapp.model.Producto;
import co.edu.uniquindio.marketplace.marketplaceapp.model.Comentario;
import co.edu.uniquindio.marketplace.marketplaceapp.utils.DataUtil;

public class ModelFactory {
    private static ModelFactory modelFactory;
    private final RedSocial redSocial;

    private ModelFactory() {
        this.redSocial = RedSocial.getInstance();
        inicializarDatos();

    }

    public static ModelFactory getInstance() {
        if (modelFactory == null) {
            modelFactory = new ModelFactory();
        }
        return modelFactory;
    }

    public RedSocial getRedSocial() {
        return redSocial;
    }

    public void agregarVendedor(Vendedor vendedor) {
        redSocial.agregarVendedor(vendedor);
    }

    public void agregarProducto(Vendedor vendedor, Producto producto) {
        vendedor.agregarProducto(producto);
    }

    public void agregarLike(Producto producto, String usuario) {
        producto.agregarLike(usuario);
    }

    public void agregarComentario(Producto producto, Comentario comentario) {
        producto.agregarComentario(comentario);
    }
    private void inicializarDatos() {
        redSocial.getVendedores().addAll(DataUtil.cargarVendedoresIniciales());
    }
}