@RestController
@RequestMapping("/grupos")
public class GrupoController {

    @Autowired
    private GrupoService grupoService;

    @PostMapping("/crear")
    public ResponseEntity<Grupo> crearGrupo(@RequestParam String creadorId,
                                            @RequestParam String nombre,
                                            @RequestParam String descripcion) {
        return ResponseEntity.ok(grupoService.crearGrupo(creadorId, nombre, descripcion));
    }

    @PostMapping("/{grupoId}/invitar")
    public ResponseEntity<Grupo> invitar(@PathVariable String grupoId, @RequestParam String usuarioId) {
        return ResponseEntity.ok(grupoService.invitarMiembro(grupoId, usuarioId));
    }

    @PostMapping("/{grupoId}/expulsar")
    public ResponseEntity<Grupo> expulsar(@PathVariable String grupoId, @RequestParam String usuarioId) {
        return ResponseEntity.ok(grupoService.expulsarMiembro(grupoId, usuarioId));
    }

    @PostMapping("/{grupoId}/abandonar")
    public ResponseEntity<Void> abandonar(@PathVariable String grupoId, @RequestParam String usuarioId) {
        grupoService.abandonarGrupo(grupoId, usuarioId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{grupoId}")
    public ResponseEntity<Void> eliminar(@PathVariable String grupoId) {
        grupoService.eliminarGrupo(grupoId);
        return ResponseEntity.ok().build();
    }
}
