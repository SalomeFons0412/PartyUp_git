@Service
public class GrupoService {

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Crear grupo
    public Grupo crearGrupo(String creadorId, String nombre, String descripcion) {
        Usuario creador = usuarioRepository.findById(creadorId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Grupo grupo = new Grupo();
        grupo.setNombre(nombre);
        grupo.setDescripcion(descripcion);
        grupo.setCreador(creador);
        grupo.agregarMiembro(creador); // el creador también es miembro

        return grupoRepository.save(grupo);
    }

    // Invitar miembro
    public Grupo invitarMiembro(String grupoId, String usuarioId) {
        Grupo grupo = grupoRepository.findById(grupoId)
                .orElseThrow(() -> new RuntimeException("Grupo no encontrado"));

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        grupo.agregarMiembro(usuario);
        return grupoRepository.save(grupo);
    }

    // Expulsar miembro
    public Grupo expulsarMiembro(String grupoId, String usuarioId) {
        Grupo grupo = grupoRepository.findById(grupoId)
                .orElseThrow(() -> new RuntimeException("Grupo no encontrado"));

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        grupo.expulsarMiembro(usuario);
        return grupoRepository.save(grupo);
    }

    // Abandonar grupo
    public void abandonarGrupo(String grupoId, String usuarioId) {
        Grupo grupo = grupoRepository.findById(grupoId)
                .orElseThrow(() -> new RuntimeException("Grupo no encontrado"));

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        grupo.expulsarMiembro(usuario);
        grupoRepository.save(grupo);
    }

    // Eliminar grupo
    public void eliminarGrupo(String grupoId) {
        grupoRepository.deleteById(grupoId);
    }
}
