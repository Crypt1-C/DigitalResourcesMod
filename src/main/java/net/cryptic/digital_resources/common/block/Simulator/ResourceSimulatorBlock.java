package net.cryptic.digital_resources.common.block.Simulator;

import net.cryptic.digital_resources.registry.BlockEntityRegistry;
import net.cryptic.digital_resources.registry.ParticleRegistry;
import net.minecraft.client.particle.Particle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleGroup;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class ResourceSimulatorBlock extends BaseEntityBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public static final BooleanProperty WORKING = BooleanProperty.create("working");

    public ResourceSimulatorBlock(Properties pProperties) {
        super(pProperties.sound(SoundType.NETHERITE_BLOCK));
    }

    private static final VoxelShape SHAPE =  Block.box(0, 0, 0, 16, 16, 16);

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    // Facing
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite()).setValue(WORKING,false);
    }

    @Override
    public BlockState rotate(BlockState pState, Rotation pRotation) {
        return pState.setValue(FACING, pRotation.rotate(pState.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
        pBuilder.add(WORKING);
    }

    /* BLOCK ENTITY */

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pState.getBlock() != pNewState.getBlock()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof ResourceSimulatorBlockEntity) {
                ((ResourceSimulatorBlockEntity) blockEntity).drops();
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide()) {
            BlockEntity entity = pLevel.getBlockEntity(pPos);
            if(entity instanceof ResourceSimulatorBlockEntity) {
                NetworkHooks.openScreen(((ServerPlayer)pPlayer), (ResourceSimulatorBlockEntity)entity, pPos);
            } else {
                throw new IllegalStateException("Our Container provider is missing!");
            }
        }
        return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }

    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, Random pRand) {
        if (pState.getValue(WORKING)) {
            double d0 = (double)pPos.getX() + 0.5D;
            double d1 = (double)pPos.getY();
            double d2 = (double)pPos.getZ() + 0.5D;

            //pLevel.playLocalSound(d0, d1, d2, ModSounds.SIMULATOR_BLOCK_WORKING.get(), SoundSource.BLOCKS, 1.0F, 1.0F, false);

            Direction direction = pState.getValue(FACING);
            Direction.Axis direction$axis = direction.getAxis();

            pLevel.addParticle(ParticleRegistry.BINARY_PARTICLE.get(), d0 + 1, d1 + 1, d2 + 1, 0.0D, 0.0D, 0.0D);
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new ResourceSimulatorBlockEntity(pPos, pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return createTickerHelper(pBlockEntityType, BlockEntityRegistry.RESOURCE_SIMULATOR.get(), ResourceSimulatorBlockEntity::tick);
    }
}
